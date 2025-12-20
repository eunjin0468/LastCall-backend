package org.example.lastcall.common.config;

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

  // Dead Letter Queue (최종 실패 메시지 보관)
  public static final String AUCTION_START_DLQ = "auction.start.dlq";
  public static final String AUCTION_END_DLQ = "auction.end.dlq";
  public static final String AUCTION_START_DLQ_KEY = "auction.start.dlq.key";
  public static final String AUCTION_END_DLQ_KEY = "auction.end.dlq.key";

  public static final long AUCTION_RETRY_TTL_MS = 5000L;

  // delayed exchange: 정상 이벤트의 의도된 실행 지연용 (비즈니스 지연)
  @Bean
  public CustomExchange auctionExchange() {
    Map<String, Object> args = Map.of("x-delayed-type", "direct");
    return new CustomExchange(AUCTION_EXCHANGE, "x-delayed-message", true, false, args);
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean(name = "auctionRabbitTemplate")
  public RabbitTemplate auctionRabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());

    return rabbitTemplate;
  }

  @Bean
  public Queue auctionStartQueue() {
    return QueueBuilder.durable(AUCTION_START_QUEUE)
        .withArgument("x-dead-letter-exchange", AUCTION_DLX)
        .withArgument("x-dead-letter-routing-key", AUCTION_START_RETRY_KEY)
        .build();
  }

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
      CustomExchange auctionExchange) {
    return BindingBuilder.bind(auctionEndQueue).to(auctionExchange).with(AUCTION_END_KEY).noargs();
  }

  // Dead Letter Exchange (DLX)
  @Bean
  public DirectExchange auctionDLX() {
    return new DirectExchange(AUCTION_DLX);
  }

  // retry queue: 시스템 예외 발생 시 재시도
  @Bean
  public Queue auctionStartRetryQueue() {
    return QueueBuilder.durable(AUCTION_START_RETRY_QUEUE)
        .withArgument("x-dead-letter-exchange", AUCTION_EXCHANGE)
        .withArgument("x-dead-letter-routing-key", AUCTION_START_KEY)
        .withArgument("x-message-ttl", AUCTION_RETRY_TTL_MS) // retry delay
        .build();
  }

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

  // Dead Letter Queue (최종 실패 메시지 보관용)
  @Bean
  public Queue auctionStartDLQ() {
    return QueueBuilder.durable(AUCTION_START_DLQ).build();
  }

  @Bean
  public Queue auctionEndDLQ() {
    return QueueBuilder.durable(AUCTION_END_DLQ).build();
  }

  // DLQ Binding
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
