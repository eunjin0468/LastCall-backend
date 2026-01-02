package org.example.lastcall.domain.auction.repository;

import org.example.lastcall.domain.auction.entity.FailedEvent;
import org.example.lastcall.domain.auction.enums.AuctionEventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DLQ 실패 이벤트 저장소
 */
public interface FailedEventRepository extends JpaRepository<FailedEvent, Long> {

    /**
     * 특정 경매 ID의 실패 이벤트 조회
     */
    List<FailedEvent> findByAuctionIdOrderByCreatedAtDesc(Long auctionId);

    /**
     * 특정 기간 동안의 실패 이벤트 조회 (페이징)
     */
    @Query("SELECT f FROM FailedEvent f WHERE f.createdAt BETWEEN :startDate AND :endDate ORDER BY f.createdAt DESC")
    Page<FailedEvent> findByCreatedAtBetween(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        Pageable pageable
    );

    /**
     * 이벤트 타입별 실패 이벤트 조회 (페이징)
     */
    Page<FailedEvent> findByEventTypeOrderByCreatedAtDesc(AuctionEventType eventType, Pageable pageable);

    /**
     * 최근 실패 이벤트 조회
     */
    @Query("SELECT f FROM FailedEvent f ORDER BY f.createdAt DESC")
    Page<FailedEvent> findRecentFailedEvents(Pageable pageable);

    /**
     * 미처리 실패 이벤트 조회 (자동 보정 대상)
     * - processed = false
     * - correctionAttempts < maxAttempts (중복 제거를 위한 파라미터화)
     * - 배치 크기 50건으로 제한 (트랜잭션 시간 제어: 약 5초)
     *
     * @param maxAttempts 최대 보정 시도 횟수
     * @return 미처리 실패 이벤트 목록 (최대 50건)
     */
    @Query("SELECT f FROM FailedEvent f WHERE f.processed = false AND f.correctionAttempts < :maxAttempts ORDER BY f.createdAt ASC LIMIT 50")
    List<FailedEvent> findUnprocessedForCorrection(@Param("maxAttempts") int maxAttempts);
}