package org.example.lastcall.domain.auction.service.event;

import lombok.RequiredArgsConstructor;
import org.example.lastcall.common.config.AuctionRabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    // 경매 시작 이벤트를 큐로 발행하는 메서드
    public void sendAuctionStartEvent(AuctionEvent event, Long delayMillis) {
        rabbitTemplate.convertAndSend(
                AuctionRabbitMqConfig.AUCTION_EXCHANGE,
                AuctionRabbitMqConfig.AUCTION_START_KEY,
                event,
                message -> {
                    message.getMessageProperties().setHeader("x-delay", delayMillis);

                    return message;
                });
    }

    // 경매 종료 이벤트를 큐로 발행하는 메서드
    public void sendAuctionEndEvent(AuctionEvent event, Long delayMillis) {
        rabbitTemplate.convertAndSend(
                AuctionRabbitMqConfig.AUCTION_EXCHANGE,
                AuctionRabbitMqConfig.AUCTION_END_KEY,
                event,
                message -> {
                    message.getMessageProperties().setHeader("x-delay", delayMillis);

                    return message;
                });
    }
}
