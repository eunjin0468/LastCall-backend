package org.example.lastcall.domain.auction.service.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lastcall.common.config.AuctionRabbitMqConfig;
import org.example.lastcall.domain.auction.entity.FailedEvent;
import org.example.lastcall.domain.auction.enums.AuctionEventType;
import org.example.lastcall.domain.auction.repository.FailedEventRepository;
import org.example.lastcall.domain.auction.service.command.AuctionCommandService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionEventListener {

  private final AuctionCommandService auctionCommandService;
  private final AuctionEventProcessor auctionEventConsumer;
  private final FailedEventRepository failedEventRepository;
  private final ObjectMapper objectMapper;

  // 경매 시작 이벤트 처리 메서드
  @RabbitListener(queues = AuctionRabbitMqConfig.AUCTION_START_QUEUE)
  public void handleAuctionStart(AuctionEvent event, Message message, Channel channel) {
    auctionEventConsumer.processEvent(event, message, channel, auctionCommandService::startAuction,
        "[RabbitMQ] 경매 시작", AuctionEventType.START);
  }

  // 경매 종료 이벤트 처리 메서드
  @RabbitListener(queues = AuctionRabbitMqConfig.AUCTION_END_QUEUE)
  public void handleAuctionEnd(AuctionEvent event, Message message, Channel channel) {
    auctionEventConsumer.processEvent(event, message, channel, auctionCommandService::closeAuction,
        "[RabbitMQ] 경매 종료", AuctionEventType.END);
  }

  /**
   * 경매 시작 DLQ 메시지 처리
   *
   * 최대 재시도 횟수를 초과한 경매 시작 이벤트를 처리합니다.
   * - 에러 로그 기록
   * - DB에 실패 이벤트 저장
   * - 메시지 ACK 처리 (DLQ에서 제거)
   */
  @RabbitListener(queues = AuctionRabbitMqConfig.AUCTION_START_DLQ)
  public void handleAuctionStartDLQ(AuctionEvent event, Message message, Channel channel) {
    processDLQMessage(event, message, channel, AuctionEventType.START);
  }

  /**
   * 경매 종료 DLQ 메시지 처리
   *
   * 최대 재시도 횟수를 초과한 경매 종료 이벤트를 처리합니다.
   * - 에러 로그 기록
   * - DB에 실패 이벤트 저장
   * - 메시지 ACK 처리 (DLQ에서 제거)
   */
  @RabbitListener(queues = AuctionRabbitMqConfig.AUCTION_END_DLQ)
  public void handleAuctionEndDLQ(AuctionEvent event, Message message, Channel channel) {
    processDLQMessage(event, message, channel, AuctionEventType.END);
  }

  /**
   * DLQ 메시지 공통 처리 로직
   */
  private void processDLQMessage(AuctionEvent event, Message message, Channel channel, AuctionEventType eventType) {
    try {
      // 1. 에러 로그 기록
      log.error("[DLQ] {} 이벤트 최종 실패: auctionId={}, version={}, headers={}",
          eventType, event.getAuctionId(), event.getVersion(),
          message.getMessageProperties().getHeaders());

      // 2. DB에 실패 이벤트 저장
      FailedEvent failedEvent = createFailedEvent(event, message, eventType);
      failedEventRepository.save(failedEvent);

      log.info("[DLQ] 실패 이벤트 DB 저장 완료: failedEventId={}, auctionId={}",
          failedEvent.getId(), event.getAuctionId());

      // 3. 메시지 ACK (DLQ에서 제거)
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

      log.info("[DLQ] {} 이벤트 처리 완료: auctionId={}", eventType, event.getAuctionId());

    } catch (Exception e) {
      log.error("[DLQ] {} 이벤트 처리 중 예외 발생: auctionId={}", eventType, event.getAuctionId(), e);
      // DLQ 처리 실패 시에도 ACK 처리 (무한 루프 방지)
      try {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
      } catch (IOException ioEx) {
        log.error("[DLQ] ACK 실패", ioEx);
      }
    }
  }

  /**
   * FailedEvent 엔티티 생성
   */
  private FailedEvent createFailedEvent(AuctionEvent event, Message message, AuctionEventType eventType) {
    try {
      String eventPayload = objectMapper.writeValueAsString(event);
      String errorMessage = extractErrorMessage(message);
      String errorStackTrace = extractStackTrace(message);
      Integer retryCount = extractRetryCount(message, eventType);
      String rabbitMessageId = message.getMessageProperties().getMessageId();
      String correlationId = message.getMessageProperties().getCorrelationId();

      return FailedEvent.builder()
          .auctionId(event.getAuctionId())
          .eventType(eventType)
          .eventVersion(event.getVersion())
          .eventPayload(eventPayload)
          .errorMessage(errorMessage)
          .errorStackTrace(errorStackTrace)
          .retryCount(retryCount)
          .rabbitMessageId(rabbitMessageId)
          .correlationId(correlationId)
          .build();
    } catch (Exception e) {
      log.error("[DLQ] FailedEvent 생성 중 예외 발생", e);
      return FailedEvent.builder()
          .auctionId(event.getAuctionId())
          .eventType(eventType)
          .eventVersion(event.getVersion())
          .eventPayload(event.toString())
          .errorMessage("FailedEvent 생성 실패: " + e.getMessage())
          .errorStackTrace(getStackTraceAsString(e))
          .retryCount(0)
          .rabbitMessageId(null)
          .correlationId(null)
          .build();
    }
  }

  /**
   * 메시지 헤더에서 에러 메시지 추출
   */
  private String extractErrorMessage(Message message) {
    Map<String, Object> headers = message.getMessageProperties().getHeaders();
    Object xDeathHeader = headers.get("x-death");

    if (xDeathHeader instanceof List<?> xDeathList && !xDeathList.isEmpty()) {
      Object firstDeath = xDeathList.get(0);
      if (firstDeath instanceof Map<?, ?> deathMap) {
        Object reason = deathMap.get("reason");
        if (reason != null) {
          return "DLQ Reason: " + reason;
        }
      }
    }

    return "최대 재시도 횟수(" + AuctionEventProcessor.MAX_RETRY_COUNT + "회) 초과";
  }

  /**
   * 스택 트레이스 추출 (x-death 헤더에서)
   */
  private String extractStackTrace(Message message) {
    Map<String, Object> headers = message.getMessageProperties().getHeaders();
    Object xException = headers.get("x-exception-stacktrace");

    if (xException instanceof byte[] bytes) {
      return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
    }

    return "Stack trace not available";
  }

  /**
   * 재시도 횟수 추출
   */
  private Integer extractRetryCount(Message message, AuctionEventType eventType) {
    String originQueue = switch (eventType) {
      case START -> AuctionRabbitMqConfig.AUCTION_START_QUEUE;
      case END -> AuctionRabbitMqConfig.AUCTION_END_QUEUE;
      default -> throw new IllegalArgumentException("Unsupported eventType: " + eventType);
    };

    Map<String, Object> headers = message.getMessageProperties().getHeaders();
    Object xDeathHeader = headers.get("x-death");

    if (xDeathHeader instanceof List<?> xDeathList) {
      for (Object entry : xDeathList) {
        if (entry instanceof Map<?, ?> deathMap) {
          Object queue = deathMap.get("queue");
          Object count = deathMap.get("count");
          if (originQueue.equals(queue) && count instanceof Long c) {
            return c.intValue();
          }
        }
      }
    }

    return AuctionEventProcessor.MAX_RETRY_COUNT;
  }

  /**
   * Exception을 String으로 변환
   */
  private String getStackTraceAsString(Exception e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    return sw.toString();
  }
}