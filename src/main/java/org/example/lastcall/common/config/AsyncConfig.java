package org.example.lastcall.common.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

/**
 * 비동기 작업 설정
 * Slack 알림 등 부가 기능을 비동기로 처리하여 메인 로직에 영향을 주지 않도록 함
 */
@Configuration
@EnableAsync
public class AsyncConfig {

  @Bean(name = "slackAlertExecutor")
  public Executor slackAlertExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);           // 기본 스레드 2개
    executor.setMaxPoolSize(5);            // 최대 스레드 5개
    executor.setQueueCapacity(100);        // 큐 대기 100개
    executor.setThreadNamePrefix("slack-alert-");
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(30);
    executor.initialize();
    return executor;
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}