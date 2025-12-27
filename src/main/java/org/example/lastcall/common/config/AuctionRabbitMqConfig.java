package org.example.lastcall.common.config;

import org.example.lastcall.domain.auction.service.event.DlqPublishTracker;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

@Configuration
public class AuctionRabbitMqConfig {

  public static final String AUCTION_EXCHANGE = "auction.exchange";
  public static final String AUCTION_START_QUEUE = "auction.start.queue";
  public static final String AUCTION_END_QUEUE = "auction.end.queue";
  public static final String AUCTION_START_KEY = "auction.start.key";
  public static final String AUCTION_END_KEY = "auction.end.key";

  public static final String AUCTION_DLX = "auction.dlx";
  public static final String AUCTION_START_RETRY_QUEUE = "auction.start.retry.queue";
  public static final String AUCTION_END_RETRY_QUEUE = "auction.end.retry.queue";
  public static final String AUCTION_START_RETRY_KEY = "auction.start.retry.key";
  public static final String AUCTION_END_RETRY_KEY = "auction.end.retry.key";

  public static final String AUCTION_START_DLQ = "auction.start.dlq";
  public static final String AUCTION_END_DLQ = "auction.end.dlq";
  public static final String AUCTION_START_DLQ_KEY = "auction.start.dlq.key";
  public static final String AUCTION_END_DLQ_KEY = "auction.end.dlq.key";

  public static final long AUCTION_RETRY_TTL_MS = 5000L;

  /**
   * Delayed Exchange(type: x-delayed-message)
   * 메시지 헤더의 {@code x-delay(ms)} 값을 이용해 발행 시점을 지연한다.
   */
  @Bean
  public CustomExchange auctionExchange() {
    Map<String, Object> args = Map.of("x-delayed-type", "direct");
    return new CustomExchange(AUCTION_EXCHANGE, "x-delayed-message", true, false, args);
  }

  /**
   * JSON 직렬화/역직렬화를 위한 messageConverter
   * @return
   */
  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  /**
   * Auction 도메인 이벤트 발행용 RabbitTemplate
   * {@link #messageConverter()}를 적용해 객체를 JSON으로 변환하여 전송한다.
   */
  @Bean(name = "auctionRabbitTemplate")
  public RabbitTemplate auctionRabbitTemplate(ConnectionFactory connectionFactory, DlqPublishTracker dlqPublishTracker) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());

    rabbitTemplate.setMandatory(true);
    rabbitTemplate.setReturnsCallback(returned -> {
      String corrId = returned.getMessage().getMessageProperties().getCorrelationId();
      if (corrId != null) {
        dlqPublishTracker.markAsReturned(corrId);
      }
    });

    return rabbitTemplate;
  }

  /**
   * 경매 시작 이벤트 처리 큐
   * 시스템 예외로 NACK(requeue=false) 처리되면 DLX로 이동하며, Retry 라우팅 키를 사용한다.
   */
  @Bean
  public Queue auctionStartQueue() {
    return QueueBuilder.durable(AUCTION_START_QUEUE)
        .withArgument("x-dead-letter-exchange", AUCTION_DLX)
        .withArgument("x-dead-letter-routing-key", AUCTION_START_RETRY_KEY)
        .build();
  }

  /**
   * 경매 종료 이벤트 처리 큐
   * 시스템 예외로 NACK(requeue=false) 처리되면 DLX로 이동하며, Retry 라우팅 키를 사용한다.
   */
  @Bean
  public Queue auctionEndQueue() {
    return QueueBuilder.durable(AUCTION_END_QUEUE)
        .withArgument("x-dead-letter-exchange", AUCTION_DLX)
        .withArgument("x-dead-letter-routing-key", AUCTION_END_RETRY_KEY)
        .build();
  }

  @Bean
  public Binding auctionStartBinding(
      @Qualifier("auctionStartQueue") Queue auctionStartQueue,
      @Qualifier("auctionExchange") CustomExchange auctionExchange) {
    return BindingBuilder.bind(auctionStartQueue).to(auctionExchange).with(AUCTION_START_KEY).noargs();
  }

  @Bean
  public Binding auctionEndBinding(
      @Qualifier("auctionEndQueue") Queue auctionEndQueue,
      @Qualifier("auctionExchange") CustomExchange auctionExchange) {
    return BindingBuilder.bind(auctionEndQueue).to(auctionExchange).with(AUCTION_END_KEY).noargs();
  }

  // DLX: 원본 큐에서 NACK(requeue=false)된 메시지가 이동하는 Dead Letter Exchange
  @Bean
  public DirectExchange auctionDLX() {
    return new DirectExchange(AUCTION_DLX);
  }

  /**
   * 시작 이벤트 Retry 큐
   * 시스템 예외(일시 장애) 발생 시 TTL 지연 후 원본 큐로 재투입
   * TTL({@link #AUCTION_RETRY_TTL_MS}) 만료 시 {@code auction.exchange}로 재발행되어 원본 큐로 복귀한다.
   */
  @Bean
  public Queue auctionStartRetryQueue() {
    return QueueBuilder.durable(AUCTION_START_RETRY_QUEUE)
        .withArgument("x-dead-letter-exchange", AUCTION_EXCHANGE)
        .withArgument("x-dead-letter-routing-key", AUCTION_START_KEY)
        .withArgument("x-message-ttl", AUCTION_RETRY_TTL_MS) // retry delay
        .build();
  }

  /**
   * 종료 이벤트 Retry 큐
   * TTL({@link #AUCTION_RETRY_TTL_MS}) 만료 시 {@code auction.exchange}로 재발행되어 원본 큐로 복귀한다.
   */
  @Bean
  public Queue auctionEndRetryQueue() {
    return QueueBuilder.durable(AUCTION_END_RETRY_QUEUE)
        .withArgument("x-dead-letter-exchange", AUCTION_EXCHANGE)
        .withArgument("x-dead-letter-routing-key", AUCTION_END_KEY)
        .withArgument("x-message-ttl", AUCTION_RETRY_TTL_MS)
        .build();
  }

  // Retry Queue Binding
  @Bean
  public Binding auctionStartRetryBinding(
      @Qualifier("auctionStartRetryQueue") Queue auctionStartRetryQueue,
      DirectExchange auctionDLX) {
    return BindingBuilder
        .bind(auctionStartRetryQueue)
        .to(auctionDLX)
        .with(AUCTION_START_RETRY_KEY);
  }

  @Bean
  public Binding auctionEndRetryBinding(
      @Qualifier("auctionEndRetryQueue") Queue auctionEndRetryQueue,
      DirectExchange auctionDLX) {
    return BindingBuilder
        .bind(auctionEndRetryQueue)
        .to(auctionDLX)
        .with(AUCTION_END_RETRY_KEY);
  }

  // DLQ Queue: 최종 실패 메시지 보관
  @Bean
  public Queue auctionStartDLQ() {
    return QueueBuilder.durable(AUCTION_START_DLQ).build();
  }

  @Bean
  public Queue auctionEndDLQ() {
    return QueueBuilder.durable(AUCTION_END_DLQ).build();
  }

  // DLQ Binding: DLX에 바인딩하여 DLQ 라우팅 키로 들어오는 메시지를 수신
  @Bean
  public Binding auctionStartDLQBinding(
      @Qualifier("auctionStartDLQ") Queue auctionStartDLQ, DirectExchange auctionDLX) {
    return BindingBuilder
        .bind(auctionStartDLQ)
        .to(auctionDLX)
        .with(AUCTION_START_DLQ_KEY);
  }

  @Bean
  public Binding auctionEndDLQBinding(
      @Qualifier("auctionEndDLQ") Queue auctionEndDLQ, DirectExchange auctionDLX) {
    return BindingBuilder
        .bind(auctionEndDLQ)
        .to(auctionDLX)
        .with(AUCTION_END_DLQ_KEY);
  }
}
