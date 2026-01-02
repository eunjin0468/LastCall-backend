package org.example.lastcall.domain.auction.service.event;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lastcall.domain.auction.entity.Auction;
import org.example.lastcall.domain.auction.entity.FailedEvent;
import org.example.lastcall.domain.auction.enums.AuctionEventType;
import org.example.lastcall.domain.auction.enums.AuctionStatus;
import org.example.lastcall.domain.auction.repository.AuctionRepository;
import org.example.lastcall.domain.auction.repository.FailedEventRepository;
import org.example.lastcall.domain.auction.service.command.AuctionCommandService;
import org.example.lastcall.domain.auction.service.notification.SlackAlertService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * DLQ 자동 보정 서비스
 *
 * 실패한 경매 이벤트를 주기적으로 자동 재시도하여 일시적 장애로 인한 데이터 불일치를 해소합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DLQCorrectionService {

  private static final int MAX_CORRECTION_ATTEMPTS = 3;

  private final FailedEventRepository failedEventRepository;
  private final AuctionRepository auctionRepository;
  private final AuctionCommandService auctionCommandService;
  private final SlackAlertService slackAlertService;

  /**
   * DLQ 자동 보정 스케줄러
   * 1분마다 실행되며, 미처리 실패 이벤트를 재시도합니다.
   */
  @Scheduled(fixedDelay = 60000) // 1분마다
  @Transactional
  public void autoCorrectFailedEvents() {
    List<FailedEvent> unprocessed = failedEventRepository.findUnprocessedForCorrection();

    if (unprocessed.isEmpty()) {
      return;
    }

    log.info("[DLQ 자동보정] 미처리 이벤트 {}건 발견", unprocessed.size());

    for (FailedEvent event : unprocessed) {
      try {
        correctFailedEvent(event);
      } catch (Exception e) {
        log.error("[DLQ 자동보정] 예상치 못한 예외 발생: failedEventId={}", event.getId(), e);
      }
    }
  }

  /**
   * 개별 실패 이벤트 보정
   */
  private void correctFailedEvent(FailedEvent event) {
    // 보정 시도 횟수 제한
    if (event.getCorrectionAttempts() >= MAX_CORRECTION_ATTEMPTS) {
      log.warn("[DLQ 자동보정] 보정 시도 횟수 초과 - 수동 개입 필요: failedEventId={}, auctionId={}, attempts={}",
          event.getId(), event.getAuctionId(), event.getCorrectionAttempts());
      return;
    }

    log.info("[DLQ 자동보정] 보정 시작: failedEventId={}, auctionId={}, eventType={}, attempt={}",
        event.getId(), event.getAuctionId(), event.getEventType(), event.getCorrectionAttempts() + 1);

    try {
      // 경매 존재 여부 확인
      Auction auction = auctionRepository.findById(event.getAuctionId())
          .orElse(null);

      if (auction == null) {
        log.warn("[DLQ 자동보정] 경매 미존재 - 보정 불가: failedEventId={}, auctionId={}",
            event.getId(), event.getAuctionId());
        event.markAsProcessed(); // 더 이상 시도하지 않음
        failedEventRepository.save(event);
        return;
      }

      // 버전 일치 확인 (멱등성)
      if (!Objects.equals(auction.getEventVersion(), event.getEventVersion())) {
        log.info("[DLQ 자동보정] 버전 불일치 - 이미 다른 경로로 처리됨: failedEventId={}, auctionId={}, expected={}, actual={}",
            event.getId(), event.getAuctionId(), event.getEventVersion(), auction.getEventVersion());
        event.markAsProcessed();
        failedEventRepository.save(event);
        return;
      }

      // 이벤트 타입별 보정 처리
      boolean corrected = processCorrectionByEventType(event, auction);

      if (corrected) {
        log.info("[DLQ 자동보정] 보정 성공: failedEventId={}, auctionId={}, eventType={}",
            event.getId(), event.getAuctionId(), event.getEventType());
      }
        // 보정이 성공했거나 불필요한 경우(이미 처리됨) 모두 processed로 마킹
        event.markAsProcessed();
        failedEventRepository.save(event);

    } catch (Exception e) {
      log.error("[DLQ 자동보정] 보정 실패: failedEventId={}, auctionId={}, eventType={}, attempt={}",
          event.getId(), event.getAuctionId(), event.getEventType(),
          event.getCorrectionAttempts() + 1, e);

      event.incrementCorrectionAttempts();
      failedEventRepository.save(event);

      // 최종 실패 시 Slack 알림
      if (event.getCorrectionAttempts() >= MAX_CORRECTION_ATTEMPTS) {
        log.error("[DLQ 자동보정] 최종 실패 - Slack 알림 전송: failedEventId={}, auctionId={}",
            event.getId(), event.getAuctionId());
        slackAlertService.sendCorrectionFailedAlert(event, e.getMessage());
      }
    }
  }

  /**
   * 이벤트 타입별 보정 처리
   *
   * @return true: 보정 실행됨, false: 보정 불필요 (이미 처리됨)
   */
  private boolean processCorrectionByEventType(FailedEvent event, Auction auction) {
    return switch (event.getEventType()) {
      case START -> correctStartEvent(event, auction);
      case END -> correctEndEvent(event, auction);
      default -> {
        log.warn("[DLQ 자동보정] 지원하지 않는 이벤트 타입: eventType={}", event.getEventType());
        yield false;
      }
    };
  }

  /**
   * 경매 시작 이벤트 보정
   */
  private boolean correctStartEvent(FailedEvent event, Auction auction) {
    // 이미 진행 중이거나 종료된 경매는 스킵
    if (auction.getStatus() != AuctionStatus.SCHEDULED) {
      log.info("[DLQ 자동보정] 경매 시작 불필요 - 이미 처리됨: auctionId={}, status={}",
          auction.getId(), auction.getStatus());
      return false;
    }

    // 경매 시작 재시도
    auctionCommandService.startAuction(auction.getId());
    log.info("[DLQ 자동보정] 경매 시작 완료: auctionId={}", auction.getId());
    return true;
  }

  /**
   * 경매 종료 이벤트 보정
   */
  private boolean correctEndEvent(FailedEvent event, Auction auction) {
    // 이미 종료된 경매는 스킵
    if (auction.getStatus() == AuctionStatus.CLOSED ||
        auction.getStatus() == AuctionStatus.CLOSED_FAILED) {
      log.info("[DLQ 자동보정] 경매 종료 불필요 - 이미 종료됨: auctionId={}, status={}",
          auction.getId(), auction.getStatus());
      return false;
    }

    // SCHEDULED 상태면 종료 불가
    if (auction.getStatus() == AuctionStatus.SCHEDULED) {
      log.warn("[DLQ 자동보정] 경매 종료 불가 - SCHEDULED 상태: auctionId={}", auction.getId());
      return false;
    }

    // 경매 종료 재시도
    auctionCommandService.closeAuction(auction.getId());
    log.info("[DLQ 자동보정] 경매 종료 완료: auctionId={}", auction.getId());
    return true;
  }
}