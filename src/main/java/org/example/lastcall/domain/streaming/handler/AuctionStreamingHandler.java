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
  private final ConcurrentHashMap<String, UserSession> sessions = new ConcurrentHashMap<>();

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
        handleOffer(session, signalingMessage);
        break;

      case ANSWER:
        handleAnswer(session, signalingMessage);
        break;

      case ICE_CANDIDATE:
        handleIceCandidate(session, signalingMessage);
        break;

      default:
        log.warn("Unknown message type: {}", type);
    }
  }

  private void handleJoin(WebSocketSession session, SignalingMessage message) throws IOException {
    String username = message.getSender();

    UserSession userSession = message.toUserSession(session);
    sessions.put(session.getId(), userSession);

    SignalingMessage response = SignalingMessage.ofJoinResult(
        username,
        true
    );

    sendMessage(session, response);
  }

  @Override
  public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
    log.info("WebSocket connection closed: {}", session.getId());
    sessions.values()
        .removeIf(userSession -> userSession.getSession().getId().equals(session.getId()));
  }

  private void handleEnterAuctionRoom(WebSocketSession session, SignalingMessage message) {
    String userId = message.getSender();

    if (sessions.containsKey(userId)) {
      log.warn("User already in room: {}", userId);
      return;
    }

    UserSession userSession = message.toUserSession(session);
    sessions.put(userId, userSession);
    log.info("User entered auction room: {}", userId);

    broadcastToAll(message);
  }

  private void handleLeaveAuctionRoom(WebSocketSession session) {
    sessions.values()
        .removeIf(userSession -> userSession.getSession().getId().equals(session.getId()));
    log.info("User left auction room: {}", session.getId());
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
    sessions.values().forEach(userSession -> {
      try {
        sendMessage(userSession.getSession(), message);
      } catch (IOException e) {
        log.error("Failed to send message to: {}", userSession.getName(), e);
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
   * WebRTC OFFER 처리 (호스트 → 시청자)
   */
  private void handleOffer(WebSocketSession session, SignalingMessage message) {
    log.info("OFFER from {} to {}", message.getSender(), message.getReceiver());
    sendToUser(message.getReceiver(), message);
  }

  /**
   * WebRTC ANSWER 처리 (시청자 → 호스트)
   */
  private void handleAnswer(WebSocketSession session, SignalingMessage message) {
    log.info("ANSWER from {} to {}", message.getSender(), message.getReceiver());
    sendToUser(message.getReceiver(), message);
  }

  /**
   * WebRTC ICE_CANDIDATE 처리 (양방향)
   */
  private void handleIceCandidate(WebSocketSession session, SignalingMessage message) {
    log.info("ICE_CANDIDATE from {} to {}", message.getSender(), message.getReceiver());
    sendToUser(message.getReceiver(), message);
  }

  /**
   * 특정 사용자에게만 메시지 전송 (WebRTC 시그널링용)
   */
  private void sendToUser(String username, SignalingMessage message) {
    UserSession targetUser = sessions.values().stream()
        .filter(userSession -> userSession.getName().equals(username))
        .findFirst()
        .orElse(null);

    if (targetUser == null) {
      log.warn("Target user not found: {}", username);
      return;
    }

    try {
      sendMessage(targetUser.getSession(), message);
    } catch (IOException e) {
      log.error("Failed to send message to user: {}", username, e);
    }
  }
}
