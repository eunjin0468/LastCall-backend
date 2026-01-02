package org.example.lastcall.common.config;

import java.time.Duration;
import java.util.concurrent.Executor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

/**
 * 비동기 작업 및 스케줄링 설정
 * Slack 알림 등 부가 기능을 비동기로 처리하고, DLQ 자동 보정 등 주기적 작업을 스케줄링합니다.
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig {

  @Bean(name = "slackAlertExecutor")
  public Executor slackAlertExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);            // 기본 스레드 2개
    executor.setMaxPoolSize(5);             // 최대 스레드 5개
    executor.setQueueCapacity(100);         // 큐 대기 100개
    executor.setThreadNamePrefix("slack-alert-");
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(30);
    executor.initialize();
    return executor;
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder
        .setConnectTimeout(Duration.ofSeconds(5))
        .setReadTimeout(Duration.ofSeconds(5))
        .build();
  }
}