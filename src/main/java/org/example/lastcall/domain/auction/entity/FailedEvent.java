package org.example.lastcall.domain.auction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.lastcall.common.entity.BaseEntity;
import org.example.lastcall.domain.auction.enums.AuctionEventType;

/**
 * DLQ(Dead Letter Queue)에 저장된 실패 이벤트를 DB에 기록하는 엔티티
 *
 * 최대 재시도 횟수를 초과한 경매 이벤트의 실패 정보를 저장하여
 * 장애 원인 분석 및 수동 재처리를 지원합니다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "failed_events", indexes = {
    @Index(name = "idx_failed_event_auction_id", columnList = "auction_id"),
    @Index(name = "idx_failed_event_type", columnList = "event_type"),
    @Index(name = "idx_failed_event_created_at", columnList = "created_at DESC"),
    @Index(name = "idx_failed_event_processed", columnList = "processed")
})
public class FailedEvent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auction_id", nullable = false)
    private Long auctionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 20)
    private AuctionEventType eventType;

    @Column(name = "event_version", nullable = false)
    private Long eventVersion;

    @Column(name = "event_payload", columnDefinition = "TEXT")
    private String eventPayload; // 이벤트 전체 내용 (JSON)

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage; // 오류 메시지

    @Column(name = "error_stack_trace", columnDefinition = "TEXT")
    private String errorStackTrace; // 스택 트레이스

    @Column(name = "retry_count", nullable = false)
    private Integer retryCount; // 재시도 횟수

    @Column(name = "rabbit_message_id", length = 255)
    private String rabbitMessageId; // RabbitMQ 메시지 ID

    @Column(name = "correlation_id", length = 255)
    private String correlationId; // DLQ 전송 시 correlation ID

    @Column(name = "processed", nullable = false)
    private Boolean processed = false; // 보정 완료 여부

    @Column(name = "correction_attempts", nullable = false)
    private Integer correctionAttempts = 0; // 자동 보정 시도 횟수

    /**
     * 빌더 패턴을 위한 생성자
     * id는 @GeneratedValue로 자동 생성되므로 생성자 파라미터에서 제외
     */
    @Builder
    private FailedEvent(
        Long auctionId,
        AuctionEventType eventType,
        Long eventVersion,
        String eventPayload,
        String errorMessage,
        String errorStackTrace,
        Integer retryCount,
        String rabbitMessageId,
        String correlationId
    ) {
        this.auctionId = auctionId;
        this.eventType = eventType;
        this.eventVersion = eventVersion;
        this.eventPayload = eventPayload;
        this.errorMessage = errorMessage;
        this.errorStackTrace = errorStackTrace;
        this.retryCount = retryCount;
        this.rabbitMessageId = rabbitMessageId;
        this.correlationId = correlationId;
        this.processed = false;
        this.correctionAttempts = 0;
    }

    /**
     * 보정 완료 처리
     */
    public void markAsProcessed() {
        this.processed = true;
    }

    /**
     * 보정 시도 횟수 증가
     */
    public void incrementCorrectionAttempts() {
        this.correctionAttempts++;
    }
}