package org.example.lastcall.domain.auction.repository;

import org.example.lastcall.domain.auction.entity.FailedEvent;
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
    Page<FailedEvent> findByEventTypeOrderByCreatedAtDesc(String eventType, Pageable pageable);

    /**
     * 최근 실패 이벤트 조회
     */
    @Query("SELECT f FROM FailedEvent f ORDER BY f.createdAt DESC")
    Page<FailedEvent> findRecentFailedEvents(Pageable pageable);
}