package org.example.lastcall.domain.auction.service.event;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lastcall.common.config.AuctionRabbitMqConfig;
import org.example.lastcall.common.exception.BusinessException;
import org.example.lastcall.domain.auction.entity.Auction;
import org.example.lastcall.domain.auction.exception.AuctionErrorCode;
import org.example.lastcall.domain.auction.repository.AuctionRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionEventProcessor {

  public static final int MAX_RETRY_COUNT = 3;
  private final AuctionRepository auctionRepository;
  @Qualifier("auctionRabbitTemplate")
  private final RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.dlq.confirm-timeout-ms}")
  private long confirmTimeoutMs;

  private QueueType parseQueueType(String queueType) {
    try {
      return QueueType.valueOf(queueType);
    } catch (Exception e) {
      throw new IllegalArgumentException("Unsupported queueType: " + queueType, e);
    }
  }

  /**
   * 경매 이벤트 메시지 공통 처리 로직
   *
   * @param event          수신한 이벤트 페이로드
   * @param message        RabbitMQ 메시지(헤더/x-death 포함)
   * @param channel        수동 ACK/NACK 처리를 위한 채널
   * @param auctionHandler 실제 도메인 처리 로직(start/close 등)
   * @param eventType      로그 출력용 이벤트 타입 문자열
   * @param queueType      START/END 구분(재시도 카운팅 및 DLQ 라우팅에 사용)
   */
  public void processEvent(AuctionEvent event, Message message, Channel channel,
      Consumer<Long> auctionHandler, String eventType, String queueType) {
    final QueueType qt;
    try {
      qt = parseQueueType(queueType);
    } catch (IllegalArgumentException e) {
      log.error("[RabbitMQ] 지원하지 않는 queueType: queueType={}, eventType={}, auctionId={}",
          queueType, eventType, event.getAuctionId(), e);
      ackMessage(channel, message);
      throw e;
    }

    try{
    log.debug("[RabbitMQ] {} 이벤트 수신: {}", eventType, event);

      Auction auction = auctionRepository.findById(event.getAuctionId()).orElseThrow(
          () -> new BusinessException(AuctionErrorCode.AUCTION_NOT_FOUND));

      if (!Objects.equals(auction.getEventVersion(), event.getVersion())) {
        log.warn("[RabbitMQ] 무시된 이벤트: 버전 불일치 (이벤트 버전={}, 현재 버전={})", event.getVersion(),
            auction.getEventVersion());
        ackMessage(channel, message);

        return;
      }

      auctionHandler.accept(event.getAuctionId());

      ackMessage(channel, message);
      log.info("[RabbitMQ] {} 처리 완료: auctionId={}", eventType, event.getAuctionId());

    } catch (BusinessException e) {
      log.warn("[RabbitMQ] {} 비즈니스 예외 발생: auctionId={}, message={}", eventType,
          event.getAuctionId(), e.getMessage());
      ackMessage(channel, message);
    } catch (IllegalArgumentException e) {
      log.error("[RabbitMQ] 지원하지 않는 queueType: queueType={}, eventType={}, auctionId={}", queueType,
          eventType, event.getAuctionId(), e);
      // 프로그래밍 오류로 보고 fail-fast 하되, 메시지 무한 재전송을 방지하기 위해 ACK 처리
      ackMessage(channel, message);
      throw e;
    } catch (Exception e) {
      log.error("[RabbitMQ] {} 처리 중 시스템 예외 발생: auctionId={}", eventType, event.getAuctionId(), e);

      int retryCount = getRetryCount(message, qt);

      if (retryCount >= MAX_RETRY_COUNT) {
        log.error("[RabbitMQ] {} 시스템 예외 {}회 초과 - DLQ 전송 시도: auctionId={}",
            eventType, MAX_RETRY_COUNT, event.getAuctionId(), e);

        try {
          sendToDLQWithConfirm(event, qt);
          ackMessage(channel, message);  // DLQ 전송 성공 시에만 ACK
        } catch (Exception dlqEx) {
          log.error("[RabbitMQ] DLQ 전송 실패 - Retry 경로로 재이동: auctionId={}",
              event.getAuctionId(), dlqEx);
          nackMessage(channel, message, false); // DLX→RetryQueue(TTL)
        }
        return;
      }
      log.warn("[RabbitMQ] {} 시스템 예외 발생: auctionId={}, retryCount={}", eventType,
          event.getAuctionId(), retryCount, e);

      // 시스템 예외: NACK(requeue=false)로 거절하여 DLX → Retry Queue(TTL)로 이동
      nackMessage(channel, message, false);
    }
  }

  /**
   * x-death 헤더를 기반으로 재시도(Dead-letter) 횟수를 계산한다.
   *
   * x-death는 여러 큐/사유의 기록이 누적될 수 있으므로, Retry 큐의 TTL 만료(expired) 기록이 아닌원본 큐(START/END)기준으로 count를 선택한다.
   */
  private int getRetryCount(Message message, QueueType queueType) {
    String originQueue = switch (queueType) {
      case START -> AuctionRabbitMqConfig.AUCTION_START_QUEUE;
      case END -> AuctionRabbitMqConfig.AUCTION_END_QUEUE;
    };
    try {
      Object xDeath = message.getMessageProperties().getHeaders().get("x-death");
      if (xDeath instanceof java.util.List<?> list && !list.isEmpty()) {
        for (Object entry : list) {
          if (entry instanceof java.util.Map<?, ?> map) {
            Object queue = map.get("queue");
            Object count = map.get("count");
            if (originQueue.equals(queue) && count instanceof Long c) {
              return c.intValue();
            }
          }
        }
      }
    } catch (Exception ignored) {
      // 헤더 형식이 예상과 다를 수 있으므로 파싱 실패 시에도 예외를 전파하지 않고 기본값(0)으로 처리
      log.debug("[RabbitMQ] x-death 파싱 실패 fallback=0, headers={}",
          message.getMessageProperties().getHeaders(), ignored);
    }
    return 0;
  }

  /**
   * 메시지를 정상 처리(또는 재시도 불가로 판단)하여 소비를 확정(ACK)한다.
   *
   * @param channel RabbitMQ 채널
   * @param message ACK 대상 메시지
   */
  private void ackMessage(Channel channel, Message message) {
    try {
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    } catch (IOException ioEx) {
      log.error("[RabbitMQ] ACK 처리 실패", ioEx);
    }
  }

  /**
   * 메시지 처리 실패를 알리고 NACK 처리한다.
   *
   * requeue=false인 경우, 원본 큐에 DLX 설정이 되어 있으면 메시지는 Dead-letter(DLX) 경로로 이동한다.
   *
   * @param channel RabbitMQ 채널
   * @param message NACK 대상 메시지
   * @param requeue 원본 큐로 재적재 여부
   */
  private void nackMessage(Channel channel, Message message, boolean requeue) {
    try {
      channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, requeue);
    } catch (IOException ioEx) {
      log.error("[RabbitMQ] NACK 처리 실패", ioEx);
    }
  }

  // DLQ 전송 + confirm(그리고 return 여부)까지 확인
  private void sendToDLQWithConfirm(AuctionEvent event, QueueType queueType) throws Exception {
    String rk = switch (queueType) {
      case START -> AuctionRabbitMqConfig.AUCTION_START_DLQ_KEY;
      case END -> AuctionRabbitMqConfig.AUCTION_END_DLQ_KEY;
    };

    String corrId = event.getAuctionId() + "-" + UUID.randomUUID();
    CorrelationData cd = new CorrelationData(corrId);

    rabbitTemplate.convertAndSend(AuctionRabbitMqConfig.AUCTION_DLX, rk, event, msg -> {
      msg.getMessageProperties().setCorrelationId(corrId);
      return msg;
    }, cd);

    CorrelationData.Confirm confirm = cd.getFuture().get(confirmTimeoutMs, TimeUnit.MILLISECONDS);

    if (!confirm.isAck()) {
      throw new IllegalStateException("DLQ publish NOT-ACK. reason=" + confirm.getReason());
    }

    // CorrelationData.getReturned()를 사용하여 메시지 반환 여부 확인
    ReturnedMessage returned = cd.getReturned();
    if (cd.getReturned() != null) {
      throw new IllegalStateException("DLQ publish returned (unroutable). corrId=" + corrId + ", returned: " + cd.getReturned());
    }
  }

  private enum QueueType {
    START,
    END
  }
}
