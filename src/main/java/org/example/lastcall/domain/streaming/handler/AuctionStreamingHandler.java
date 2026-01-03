package org.example.lastcall.domain.streaming.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lastcall.domain.streaming.dto.SignalingMessage;
import org.example.lastcall.domain.streaming.dto.UserSession;
import org.example.lastcall.domain.streaming.enums.MessageType;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * WebSocket handler for auction streaming signaling messages. Handles message types for auction
 * room, streaming, chat, bid, and WebRTC signaling.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuctionStreamingHandler extends TextWebSocketHandler {

  private final ObjectMapper objectMapper;

  /**
   * 세션 관리 기준을 "세션 ID"로 통일합니다. - sessionsById: sessionId -> UserSession (브로드캐스트/정리 기준) -
   * sessionsByName: username -> UserSession (sendToUser O(1) 조회)
   */
  private final ConcurrentHashMap<String, UserSession> sessionsById = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, UserSession> sessionsByName = new ConcurrentHashMap<>();

  @Override
  public void afterConnectionEstablished(final WebSocketSession session) {
    log.info("WebSocket connection established: {}", session.getId());
  }

  @Override
  public void handleTextMessage(
      final WebSocketSession session,
      final TextMessage message
  ) throws IOException {
    String payload = message.getPayload();
    log.info("Received message: {}", payload);

    SignalingMessage signalingMessage = objectMapper.readValue(payload, SignalingMessage.class);
    MessageType type = MessageType.valueOf(signalingMessage.getType().trim().toUpperCase());

    switch (type) {
      case JOIN:
        handleJoin(session, signalingMessage);
        break;

      case ENTER_AUCTION_ROOM:
        handleEnterAuctionRoom(session, signalingMessage);
        break;

      case LEAVE_AUCTION_ROOM:
        handleLeaveAuctionRoom(session);
        break;

      case START_STREAM:
        handleStartStream(signalingMessage);
        break;

      case STOP_STREAM:
        handleStopStream(signalingMessage);
        break;

      case SEND_CHAT:
        handleSendChat(signalingMessage);
        break;

      case SEND_BID:
        handleSendBid(signalingMessage);
        break;

      case OFFER:
      case ANSWER:
      case ICE_CANDIDATE:
        handleSignalingRelay(signalingMessage);
        break;

      default:
        log.warn("Unknown message type: {}", type);
    }
  }

  private void handleJoin(WebSocketSession session, SignalingMessage message) throws IOException {
    String username = message.getSender();

    boolean joined = registerSession(session, message);
    SignalingMessage response = SignalingMessage.ofJoinResult(
        username,
        joined
    );

    sendMessage(session, response);
  }

  @Override
  public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
    UserSession removed = removeSession(session);
    if (removed != null) {
      log.info("WebSocket 연결 종료: user={}, sessionId={}", removed.getName(), session.getId());
    } else {
      log.warn("연결이 종료되었지만 등록된 세션 정보를 찾을 수 없습니다: {}", session.getId());
    }
  }

  private void handleEnterAuctionRoom(WebSocketSession session, SignalingMessage message) {
    String username = message.getSender();

    UserSession existing = sessionsById.get(session.getId());
    if (existing == null) {
      boolean registered = registerSession(session, message);
      if (!registered) {
        log.warn("사용자 이름이 이미 사용 중이라 입장할 수 없습니다: {}", username);
        return;
      }
    }

    log.info("사용자가 경매방에 입장하였습니다: {}", username);

    broadcastToAll(message);
  }

  private void handleLeaveAuctionRoom(WebSocketSession session) {
    UserSession removed = removeSession(session);
    if (removed != null) {
      log.info("사용자가 경매방에서 퇴장했습니다: {}", removed.getName());
      return;
    }
    log.warn("퇴장 요청을 받았으나 등록된 세션을 찾을 수 없습니다: {}", session.getId());
  }

  private void handleStartStream(SignalingMessage message) {
    log.info("Stream started by: {}", message.getSender());
    broadcastToAll(message);
  }

  private void handleStopStream(SignalingMessage message) {
    log.info("Stream stopped by: {}", message.getSender());
    broadcastToAll(message);
  }

  private void handleSendChat(SignalingMessage message) {
    log.info("Chat from {}: {}", message.getSender(), message.getData());
    broadcastToAll(message);
  }

  private void handleSendBid(SignalingMessage message) {
    log.info("Bid from {}: {}", message.getSender(), message.getData());
    broadcastToAll(message);
  }

  private void broadcastToAll(SignalingMessage message) {
    sessionsById.values().forEach(userSession -> {
      try {
        sendMessage(userSession.getSession(), message);
      } catch (IOException e) {
        log.error("메시지 전송에 실패했습니다: {}", userSession.getName(), e);
      }
    });
  }

  private void sendMessage(WebSocketSession session, SignalingMessage message) throws IOException {
    if (session.isOpen()) {
      String json = objectMapper.writeValueAsString(message);
      session.sendMessage(new TextMessage(json));
    }
  }

  /**
   * 세션 등록 (Kick-out 전략)
   * - 같은 username으로 새 접속이 들어오면 기존 접속을 종료하고 새 접속으로 전환
   * - 재접속, 새로고침 등의 상황에서 빠른 접속 가능
   */
  private boolean registerSession(WebSocketSession session, SignalingMessage message) {
    String username = message.getSender();
    UserSession newUserSession = message.toUserSession(session);

    // 1. 같은 세션이 다른 username으로 재JOIN하는 경우, 이전 username 매핑을 먼저 정리
    UserSession previous = sessionsById.get(session.getId());
    if (previous != null && !previous.getName().equals(username)) {
      sessionsByName.remove(previous.getName(), previous);
    }

    // 2. username 기준으로 새 접속 등록 (기존 접속이 있으면 덮어쓰기)
    UserSession oldUserSession = sessionsByName.put(username, newUserSession);

    // 3. Kick-out: 기존 접속이 있고 다른 접속이면 강제 종료 후 새 접속으로 전환
    if (oldUserSession != null && !oldUserSession.getSession().getId().equals(session.getId())) {
      sessionsById.remove(oldUserSession.getSession().getId());
      try {
        oldUserSession.getSession().close(CloseStatus.NORMAL);
        log.info("사용자 재접속: 세션을 갱신합니다(새로고침/네트워크 재연결) - username={}, oldSessionId={}, newSessionId={}",
            username, oldUserSession.getSession().getId(), session.getId());
      } catch (IOException e) {
        log.warn("이전 접속 종료 실패: username={}, sessionId={}", username,
            oldUserSession.getSession().getId(), e);
      }
    }

    // 4. sessionId 기준 등록
    sessionsById.put(session.getId(), newUserSession);

    return true;
  }

  /**
   * 세션 제거 (두 맵 동시 정리)
   */
  private UserSession removeSession(WebSocketSession session) {
    UserSession removed = sessionsById.remove(session.getId());
    if (removed == null) {
      return null;
    }

    // 동일 객체일 때만 제거 (동시성 상황에서의 안전장치)
    sessionsByName.remove(removed.getName(), removed);
    return removed;
  }

  /**
   * WebRTC 시그널링 메시지 릴레이 (OFFER, ANSWER, ICE_CANDIDATE)
   */
  private void handleSignalingRelay(SignalingMessage message) {
    log.info("{} from {} to {}", message.getType(), message.getSender(), message.getReceiver());
    sendToUser(message.getReceiver(), message);
  }

  /**
   * 특정 사용자에게만 메시지 전송 (WebRTC 시그널링용)
   */
  private void sendToUser(String username, SignalingMessage message) {
    UserSession targetUser = sessionsByName.get(username);

    if (targetUser == null) {
      log.warn("대상 사용자를 찾을 수 없습니다: {}", username);
      return;
    }

    try {
      sendMessage(targetUser.getSession(), message);
    } catch (IOException e) {
      log.error("사용자에게 메시지 전송에 실패했습니다: {}", username, e);
    }
  }
}
