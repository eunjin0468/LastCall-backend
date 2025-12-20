package org.example.lastcall.common.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AuctionRabbitMqConfig {

  public static final String EXCHANGE_NAME = "auction.exchange";
  public static final String START_QUEUE_NAME = "auction.start.queue";
  public static final String END_QUEUE_NAME = "auction.end.queue";
  public static final String START_ROUTING_KEY = "auction.start.key";
  public static final String END_ROUTING_KEY = "auction.end.key";

  public static final String DLX_NAME = "auction.dlx";
  public static final String START_RETRY_QUEUE = "start.retry.queue";
  public static final String END_RETRY_QUEUE = "end.retry.queue";
  public static final String START_RETRY_ROUTING_KEY = "auction.start.retry.key";
  public static final String END_RETRY_ROUTING_KEY   = "auction.end.retry.key";

  // Dead Letter Queue (최종 실패 메시지 보관)
  public static final String START_DLQ = "start.dlq";
  public static final String END_DLQ = "end.dlq";
  public static final String START_DLQ_ROUTING_KEY   = "auction.start.dlq.key";
  public static final String END_DLQ_ROUTING_KEY     = "auction.end.dlq.key";
  @Bean
  public CustomExchange delayExchange() {
    Map<String, Object> args = new HashMap<>();
    args.put("x-delayed-type", "direct");

    return new CustomExchange(EXCHANGE_NAME, "x-delayed-message", true, false, args);
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean(name = "auctionRabbitTemplate")
  public AmqpTemplate auctionRabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());

    return rabbitTemplate;
  }

  @Bean
  public Queue startQueue() {
    return QueueBuilder.durable(START_QUEUE_NAME)
        .withArgument("x-dead-letter-exchange", DLX_NAME)
        .withArgument("x-dead-letter-routing-key", START_RETRY_ROUTING_KEY)
        .build();
  }

  @Bean
  public Queue endQueue() {
    return QueueBuilder.durable(END_QUEUE_NAME)
        .withArgument("x-dead-letter-exchange", DLX_NAME)
        .withArgument("x-dead-letter-routing-key", END_RETRY_ROUTING_KEY)
        .build();
  }

  @Bean
  public Binding startBinding(Queue startQueue, CustomExchange delayExchange) {
    return BindingBuilder.bind(startQueue).to(delayExchange).with(START_ROUTING_KEY).noargs();
  }

  @Bean
  public Binding endBinding(Queue endQueue, CustomExchange delayExchange) {
    return BindingBuilder.bind(endQueue).to(delayExchange).with(END_ROUTING_KEY).noargs();
  }

  // Dead Letter Exchange (DLX)
  @Bean
  public DirectExchange auctionDLX() {
    return new DirectExchange(DLX_NAME);
  }

  // Retry Queue (지연 재시도용)
  @Bean
  public Queue startRetryQueue() {
    return QueueBuilder.durable(START_RETRY_QUEUE)
        .withArgument("x-dead-letter-exchange", EXCHANGE_NAME)
        .withArgument("x-dead-letter-routing-key", START_ROUTING_KEY)
        .withArgument("x-message-ttl", 5000) // 첫 재시도 5초 후 실행
        .build();
  }

  @Bean
  public Queue endRetryQueue() {
    return QueueBuilder.durable(END_RETRY_QUEUE)
        .withArgument("x-dead-letter-exchange", EXCHANGE_NAME)
        .withArgument("x-dead-letter-routing-key", END_ROUTING_KEY)
        .withArgument("x-message-ttl", 5000)
        .build();
  }

  // Retry Queue Binding
  @Bean
  public Binding startRetryBinding(Queue startRetryQueue, DirectExchange auctionDLX) {
    return BindingBuilder
        .bind(startRetryQueue)
        .to(auctionDLX)
        .with(START_RETRY_ROUTING_KEY);
  }

  @Bean
  public Binding endRetryBinding(Queue endRetryQueue, DirectExchange auctionDLX) {
    return BindingBuilder
        .bind(endRetryQueue)
        .to(auctionDLX)
        .with(END_RETRY_ROUTING_KEY);
  }

  // Dead Letter Queue (최종 실패 메시지 보관용)
  @Bean
  public Queue startDLQ() {
    return QueueBuilder.durable(START_DLQ).build();
  }

  @Bean
  public Queue endDLQ() {
    return QueueBuilder.durable(END_DLQ).build();
  }

  // DLQ Binding
  @Bean
  public Binding startDLQBinding(Queue startDLQ, DirectExchange auctionDLX) {
    return BindingBuilder
        .bind(startDLQ)
        .to(auctionDLX)
        .with(START_DLQ_ROUTING_KEY);
  }

  @Bean
  public Binding endDLQBinding(Queue endDLQ, DirectExchange auctionDLX) {
    return BindingBuilder
        .bind(endDLQ)
        .to(auctionDLX)
        .with(END_DLQ_ROUTING_KEY);
  }
}
