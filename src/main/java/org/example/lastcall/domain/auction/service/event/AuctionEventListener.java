package org.example.lastcall.domain.auction.service.event;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lastcall.common.config.AuctionRabbitMqConfig;
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

    // 경매 시작 이벤트 처리 메서드
    @RabbitListener(queues = AuctionRabbitMqConfig.AUCTION_START_QUEUE)
    public void handleAuctionStart(AuctionEvent event, Message message, Channel channel) {
        auctionEventConsumer.processEvent(event, message, channel, auctionCommandService::startAuction, "[RabbitMQ] 경매 시작", "START");
    }

    // 경매 종료 이벤트 처리 메서드
    @RabbitListener(queues = AuctionRabbitMqConfig.AUCTION_END_QUEUE)
    public void handleAuctionEnd(AuctionEvent event, Message message, Channel channel) {
        auctionEventConsumer.processEvent(event, message, channel, auctionCommandService::closeAuction, "[RabbitMQ] 경매 종료", "END");
    }
}