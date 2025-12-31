package org.example.lastcall.domain.auction.service.event;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lastcall.domain.auction.entity.FailedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Slack 알림 서비스
 * Incoming Webhook을 사용하여 DLQ 실패 이벤트를 Slack 채널로 전송
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SlackAlertService {

  private final RestTemplate restTemplate;

  @Value("${slack.webhook.dlq-alert}")
  private String webhookUrl;

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  /**
   * DLQ 실패 이벤트를 Slack으로 전송
   * 비동기 처리로 메인 로직에 영향을 주지 않음
   *
   * @param event 실패한 이벤트 정보
   */
  @Async("slackAlertExecutor")
  public void sendDLQAlert(FailedEvent event) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      Map<String, Object> payload = createDLQAlertPayload(event);
      HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

      restTemplate.postForEntity(webhookUrl, request, String.class);

      log.info("[Slack] DLQ 알림 전송 성공: auctionId={}, eventType={}",
          event.getAuctionId(), event.getEventType());

    } catch (Exception e) {
      // Slack 알림 실패가 서비스에 영향을 주지 않도록 예외를 삼킴
      log.error("[Slack] DLQ 알림 전송 실패 (무시됨): auctionId={}, eventType={}",
          event.getAuctionId(), event.getEventType(), e);
    }
  }

  /**
   * Slack Block Kit 형식의 메시지 페이로드 생성
   */
  private Map<String, Object> createDLQAlertPayload(FailedEvent event) {
    return Map.of(
        "text", String.format("🚨 DLQ 실패: 경매 #%d (%s)",
            event.getAuctionId(), event.getEventType()),
        "blocks", List.of(
            createHeaderBlock(),
            createInfoFieldsBlock(event),
            createErrorBlock(event),
            createContextBlock(event)
        )
    );
  }

  /**
   * 헤더 블록: 제목
   */
  private Map<String, Object> createHeaderBlock() {
    return Map.of(
        "type", "header",
        "text", Map.of(
            "type", "plain_text",
            "text", "🚨 경매 이벤트 DLQ 실패",
            "emoji", true
        )
    );
  }

  /**
   * 정보 필드 블록: 경매 ID, 타입, 재시도 횟수, 시각
   */
  private Map<String, Object> createInfoFieldsBlock(FailedEvent event) {
    return Map.of(
        "type", "section",
        "fields", List.of(
            Map.of("type", "mrkdwn", "text", "*경매 ID:*\n`" + event.getAuctionId() + "`"),
            Map.of("type", "mrkdwn", "text", "*이벤트 타입:*\n`" + event.getEventType() + "`"),
            Map.of("type", "mrkdwn", "text", "*재시도 횟수:*\n`" + event.getRetryCount() + "회`"),
            Map.of("type", "mrkdwn", "text", "*실패 시각:*\n`" + event.getCreatedAt().format(FORMATTER) + "`")
        )
    );
  }

  /**
   * 에러 메시지 블록
   */
  private Map<String, Object> createErrorBlock(FailedEvent event) {
    String errorMessage = truncate(event.getErrorMessage(), 500);
    return Map.of(
        "type", "section",
        "text", Map.of(
            "type", "mrkdwn",
            "text", "*에러 메시지:*\n```" + errorMessage + "```"
        )
    );
  }

  /**
   * 컨텍스트 블록: DB ID 및 부가 정보
   */
  private Map<String, Object> createContextBlock(FailedEvent event) {
    return Map.of(
        "type", "context",
        "elements", List.of(
            Map.of(
                "type", "mrkdwn",
                "text", "DB ID: `" + event.getId() + "` | 버전: `" + event.getEventVersion() +
                    "` | 상세 내용은 관리자 페이지에서 확인 가능"
            )
        )
    );
  }

  /**
   * 문자열 길이 제한 (Slack 메시지 크기 제한 대응)
   */
  private String truncate(String text, int maxLength) {
    if (text == null) {
      return "N/A";
    }
    if (text.length() <= maxLength) {
      return text;
    }
    return text.substring(0, maxLength) + "... (truncated)";
  }
}