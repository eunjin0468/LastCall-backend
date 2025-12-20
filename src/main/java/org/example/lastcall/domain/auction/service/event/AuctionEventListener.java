package org.example.lastcall.domain.auction.service.event;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lastcall.common.config.AuctionRabbitMqConfig;
import org.example.lastcall.common.exception.BusinessException;
import org.example.lastcall.domain.auction.entity.Auction;
import org.example.lastcall.domain.auction.exception.AuctionErrorCode;
import org.example.lastcall.domain.auction.repository.AuctionRepository;
import org.example.lastcall.domain.auction.service.command.AuctionCommandService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionEventListener {
    private final AuctionCommandService auctionCommandService;
    private final AuctionRepository auctionRepository;

    // 이벤트 처리 메서드
    @RabbitListener(queues = AuctionRabbitMqConfig.AUCTION_START_QUEUE)
    public void handleAuctionStart(AuctionEvent event, Message message, Channel channel) {
        processEvent(event, message, channel, auctionCommandService::startAuction, "[RabbitMQ] 경매 시작");
    }

    @RabbitListener(queues = AuctionRabbitMqConfig.AUCTION_END_QUEUE)
    public void handleAuctionEnd(AuctionEvent event, Message message, Channel channel) {
        processEvent(event, message, channel, auctionCommandService::closeAuction, "[RabbitMQ] 경매 종료");
    }

    // 공용 이벤트 처리 헬퍼 메서드 (메서드 분리)
    private void processEvent(AuctionEvent event, Message message, Channel channel, Consumer<Long> auctionHandler, String eventType) {
        try {
            log.debug("[RabbitMQ] {} 이벤트 수신: {}", eventType, event);

            Auction auction = auctionRepository.findById(event.getAuctionId()).orElseThrow(
                    () -> new BusinessException(AuctionErrorCode.AUCTION_NOT_FOUND));

            if (!Objects.equals(auction.getEventVersion(), event.getVersion())) {
                log.warn("[RabbitMQ] 무시된 이벤트: 버전 불일치 (이벤트 버전={}, 현재 버전={})", event.getVersion(), auction.getEventVersion());
                ackMessage(channel, message);

                return;
            }

            auctionHandler.accept(event.getAuctionId());

            // 성공 처리 시 ACK
            ackMessage(channel, message);
            log.info("[RabbitMQ] {} 처리 완료: auctionId={}", eventType, event.getAuctionId());

        } catch (BusinessException e) {
            log.warn("[RabbitMQ] {} 비즈니스 예외 발생: auctionId={}, message={}", eventType, event.getAuctionId(), e.getMessage());
            ackMessage(channel, message);
        } catch (Exception e) {
            log.error("[RabbitMQ] {} 처리 중 시스템 예외 발생: auctionId={}", eventType, event.getAuctionId(), e);
            // 재시도 횟수 설정
            int retryCount = getRetryCount(message);

            if (retryCount >= 3) {
                log.error("[RabbitMQ] {} 시스템 예외 3회 초과: auctionId={}", eventType, event.getAuctionId(), e);

                ackMessage(channel, message);
                return;
            }
            log.warn("[RabbitMQ] {} 시스템 예외 발생: auctionId={}, retryCount={}", eventType, event.getAuctionId(), retryCount, e);

            nackMessage(channel, message, false);
        }
    }


    // 재시도 메서드
    private int getRetryCount(Message message) {
        try {
            Object death = message.getMessageProperties().getHeaders().get("x-death");
            if (death instanceof java.util.List<?> list && !list.isEmpty()) {
                Object first = list.get(0);
                if (first instanceof java.util.Map<?, ?> map) {
                    Object count = map.get("count");
                    if (count instanceof Long c) return c.intValue();
                }
            }
        } catch (Exception ignored) { // retryCount = 0 처리
        }

        return 0;
    }

    // 공용 ACK 헬퍼 메서드
    private void ackMessage(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException ioEx) {
            log.error("[RabbitMQ] ACK 처리 실패", ioEx);
        }
    }

    // 공용 NACK 헬퍼 메서드
    private void nackMessage(Channel channel, Message message, boolean requeue) {
        try {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, requeue);
        } catch (IOException ioEx) {
            log.error("[RabbitMQ] NACK 처리 실패", ioEx);
        }
    }
}