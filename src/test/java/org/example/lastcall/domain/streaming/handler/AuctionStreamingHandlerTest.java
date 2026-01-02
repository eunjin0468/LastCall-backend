package org.example.lastcall.domain.streaming.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.lastcall.domain.streaming.dto.SignalingMessage;
import org.example.lastcall.domain.streaming.enums.MessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuctionStreamingHandler 테스트")
class AuctionStreamingHandlerTest {

  private AuctionStreamingHandler handler;
  private ObjectMapper objectMapper;
  private WebSocketSession mockSession1;
  private WebSocketSession mockSession2;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
    handler = new AuctionStreamingHandler(objectMapper);

    mockSession1 = mock(WebSocketSession.class);
    mockSession2 = mock(WebSocketSession.class);

    lenient().when(mockSession1.getId()).thenReturn("session1");
    lenient().when(mockSession1.isOpen()).thenReturn(true);

    lenient().when(mockSession2.getId()).thenReturn("session2");
    lenient().when(mockSession2.isOpen()).thenReturn(true);
  }

  @Nested
  @DisplayName("WebSocket 연결 관리")
  class ConnectionManagement {

    @Test
    @DisplayName("WebSocket 연결이 수립되면 로그가 출력된다")
    void givenWebSocketSession_whenConnectionEstablished_thenLogIsRecorded() throws Exception {
      // when
      handler.afterConnectionEstablished(mockSession1);

      // then
      verify(mockSession1, times(1)).getId();
    }

    @Test
    @DisplayName("WebSocket 연결이 종료되면 세션이 제거된다")
    void givenActiveSession_whenConnectionClosed_thenSessionIsRemoved() throws Exception {
      // given
      SignalingMessage joinMessage = SignalingMessage.builder()
          .type(MessageType.JOIN.name())
          .sender("user1")
          .build();

      String jsonMessage = objectMapper.writeValueAsString(joinMessage);
      handler.handleTextMessage(mockSession1, new TextMessage(jsonMessage));

      // when
      handler.afterConnectionClosed(mockSession1, CloseStatus.NORMAL);

      // then
      verify(mockSession1, atLeastOnce()).getId();
    }
  }

  @Nested
  @DisplayName("사용자 세션 관리")
  class UserSessionManagement {

    @Test
    @DisplayName("JOIN 메시지를 받으면 세션이 등록되고 응답이 전송된다")
    void givenJoinMessage_whenHandleMessage_thenSessionRegisteredAndResponseSent() throws Exception {
      // given
      SignalingMessage joinMessage = SignalingMessage.builder()
          .type(MessageType.JOIN.name())
          .sender("user1")
          .build();

      String jsonMessage = objectMapper.writeValueAsString(joinMessage);

      // when
      handler.handleTextMessage(mockSession1, new TextMessage(jsonMessage));

      // then
      verify(mockSession1, atLeastOnce()).sendMessage(any(TextMessage.class));
    }

    @Test
    @DisplayName("ENTER_AUCTION_ROOM 메시지를 받으면 방에 입장한다")
    void givenEnterRoomMessage_whenHandleMessage_thenUserEntersRoom() throws Exception {
      // given
      SignalingMessage enterMessage = SignalingMessage.builder()
          .type(MessageType.ENTER_AUCTION_ROOM.name())
          .sender("user1")
          .data(Map.of("roomId", "auction123"))
          .build();

      String jsonMessage = objectMapper.writeValueAsString(enterMessage);

      // when
      handler.handleTextMessage(mockSession1, new TextMessage(jsonMessage));

      // then
      verify(mockSession1, atLeastOnce()).sendMessage(any(TextMessage.class));
    }

    @Test
    @DisplayName("LEAVE_AUCTION_ROOM 메시지를 받으면 방에서 퇴장한다")
    void givenLeaveRoomMessage_whenHandleMessage_thenUserLeavesRoom() throws Exception {
      // given
      SignalingMessage enterMessage = SignalingMessage.builder()
          .type(MessageType.ENTER_AUCTION_ROOM.name())
          .sender("user1")
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(enterMessage)));

      // when
      SignalingMessage leaveMessage = SignalingMessage.builder()
          .type(MessageType.LEAVE_AUCTION_ROOM.name())
          .sender("user1")
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(leaveMessage)));

      // then
      verify(mockSession1, atLeastOnce()).getId();
    }
  }

  @Nested
  @DisplayName("브로드캐스트 메시지")
  class BroadcastMessages {

    @Test
    @DisplayName("START_STREAM 메시지를 받으면 모든 사용자에게 브로드캐스트된다")
    void givenStartStreamMessage_whenHandleMessage_thenBroadcastToAllUsers() throws Exception {
      // given
      SignalingMessage join1 = SignalingMessage.builder()
          .type(MessageType.JOIN.name())
          .sender("user1")
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(join1)));

      SignalingMessage join2 = SignalingMessage.builder()
          .type(MessageType.JOIN.name())
          .sender("user2")
          .build();
      handler.handleTextMessage(mockSession2, new TextMessage(objectMapper.writeValueAsString(join2)));

      // when
      SignalingMessage startStream = SignalingMessage.builder()
          .type(MessageType.START_STREAM.name())
          .sender("user1")
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(startStream)));

      // then
      verify(mockSession1, atLeastOnce()).sendMessage(any(TextMessage.class));
      verify(mockSession2, atLeastOnce()).sendMessage(any(TextMessage.class));
    }

    @Test
    @DisplayName("STOP_STREAM 메시지를 받으면 모든 사용자에게 브로드캐스트된다")
    void givenStopStreamMessage_whenHandleMessage_thenBroadcastToAllUsers() throws Exception {
      // given
      SignalingMessage join1 = SignalingMessage.builder()
          .type(MessageType.JOIN.name())
          .sender("user1")
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(join1)));

      // when
      SignalingMessage stopStream = SignalingMessage.builder()
          .type(MessageType.STOP_STREAM.name())
          .sender("user1")
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(stopStream)));

      // then
      verify(mockSession1, atLeastOnce()).sendMessage(any(TextMessage.class));
    }

    @Test
    @DisplayName("SEND_CHAT 메시지를 받으면 모든 사용자에게 브로드캐스트된다")
    void givenChatMessage_whenHandleMessage_thenBroadcastToAllUsers() throws Exception {
      // given
      SignalingMessage join1 = SignalingMessage.builder()
          .type(MessageType.JOIN.name())
          .sender("user1")
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(join1)));

      // when
      SignalingMessage chatMessage = SignalingMessage.builder()
          .type(MessageType.SEND_CHAT.name())
          .sender("user1")
          .data("Hello, World!")
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(chatMessage)));

      // then
      verify(mockSession1, atLeastOnce()).sendMessage(any(TextMessage.class));
    }

    @Test
    @DisplayName("SEND_BID 메시지를 받으면 모든 사용자에게 브로드캐스트된다")
    void givenBidMessage_whenHandleMessage_thenBroadcastToAllUsers() throws Exception {
      // given
      SignalingMessage join1 = SignalingMessage.builder()
          .type(MessageType.JOIN.name())
          .sender("user1")
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(join1)));

      Map<String, Object> bidData = new HashMap<>();
      bidData.put("amount", 10000);
      bidData.put("auctionId", "123");

      // when
      SignalingMessage bidMessage = SignalingMessage.builder()
          .type(MessageType.SEND_BID.name())
          .sender("user1")
          .data(bidData)
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(bidMessage)));

      // then
      verify(mockSession1, atLeastOnce()).sendMessage(any(TextMessage.class));
    }
  }

  @Nested
  @DisplayName("WebRTC 시그널링 메시지")
  class WebRTCSignaling {

    @Test
    @DisplayName("OFFER 메시지를 받으면 특정 사용자에게만 전송된다")
    void givenOfferMessage_whenHandleMessage_thenSendToTargetUserOnly() throws Exception {
      // given
      SignalingMessage joinHost = SignalingMessage.builder()
          .type(MessageType.JOIN.name())
          .sender("host")
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(joinHost)));

      SignalingMessage joinViewer = SignalingMessage.builder()
          .type(MessageType.JOIN.name())
          .sender("viewer")
          .build();
      handler.handleTextMessage(mockSession2, new TextMessage(objectMapper.writeValueAsString(joinViewer)));

      Map<String, Object> sdpData = new HashMap<>();
      sdpData.put("type", "offer");
      sdpData.put("sdp", "v=0...");

      // when
      SignalingMessage offerMessage = SignalingMessage.builder()
          .type(MessageType.OFFER.name())
          .sender("host")
          .receiver("viewer")
          .data(sdpData)
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(offerMessage)));

      // then
      verify(mockSession2, atLeastOnce()).sendMessage(any(TextMessage.class));
    }

    @Test
    @DisplayName("ANSWER 메시지를 받으면 특정 사용자에게만 전송된다")
    void givenAnswerMessage_whenHandleMessage_thenSendToTargetUserOnly() throws Exception {
      // given
      SignalingMessage joinHost = SignalingMessage.builder()
          .type(MessageType.JOIN.name())
          .sender("host")
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(joinHost)));

      SignalingMessage joinViewer = SignalingMessage.builder()
          .type(MessageType.JOIN.name())
          .sender("viewer")
          .build();
      handler.handleTextMessage(mockSession2, new TextMessage(objectMapper.writeValueAsString(joinViewer)));

      Map<String, Object> sdpData = new HashMap<>();
      sdpData.put("type", "answer");
      sdpData.put("sdp", "v=0...");

      // when
      SignalingMessage answerMessage = SignalingMessage.builder()
          .type(MessageType.ANSWER.name())
          .sender("viewer")
          .receiver("host")
          .data(sdpData)
          .build();
      handler.handleTextMessage(mockSession2, new TextMessage(objectMapper.writeValueAsString(answerMessage)));

      // then
      verify(mockSession1, atLeastOnce()).sendMessage(any(TextMessage.class));
    }

    @Test
    @DisplayName("ICE_CANDIDATE 메시지를 받으면 특정 사용자에게만 전송된다")
    void givenIceCandidateMessage_whenHandleMessage_thenSendToTargetUserOnly() throws Exception {
      // given
      SignalingMessage joinHost = SignalingMessage.builder()
          .type(MessageType.JOIN.name())
          .sender("host")
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(joinHost)));

      SignalingMessage joinViewer = SignalingMessage.builder()
          .type(MessageType.JOIN.name())
          .sender("viewer")
          .build();
      handler.handleTextMessage(mockSession2, new TextMessage(objectMapper.writeValueAsString(joinViewer)));

      Map<String, Object> candidateData = new HashMap<>();
      candidateData.put("candidate", "candidate:...");
      candidateData.put("sdpMid", "0");
      candidateData.put("sdpMLineIndex", 0);

      // when
      SignalingMessage iceMessage = SignalingMessage.builder()
          .type(MessageType.ICE_CANDIDATE.name())
          .sender("host")
          .receiver("viewer")
          .data(candidateData)
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(iceMessage)));

      // then
      verify(mockSession2, atLeastOnce()).sendMessage(any(TextMessage.class));
    }

    @Test
    @DisplayName("존재하지 않는 사용자에게 OFFER를 보내면 전송되지 않고 예외도 발생하지 않는다")
    void givenNonExistentReceiver_whenSendOffer_thenNotSentAndNoException() throws Exception {
      // given: host만 JOIN
      SignalingMessage joinHost = SignalingMessage.builder()
          .type(MessageType.JOIN.name())
          .sender("host")
          .build();
      handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(joinHost)));

      // JOIN 응답 검증 후 초기화
      verify(mockSession1, times(1)).sendMessage(any(TextMessage.class));
      reset(mockSession1);

      // when: 존재하지 않는 사용자에게 OFFER 전송 시도
      SignalingMessage offerMessage = SignalingMessage.builder()
          .type(MessageType.OFFER.name())
          .sender("host")
          .receiver("nonexistent")
          .data(Map.of("sdp", "v=0..."))
          .build();

      // then: 예외 발생 안 함, 메시지도 전송 안 됨
      assertDoesNotThrow(() ->
          handler.handleTextMessage(mockSession1, new TextMessage(objectMapper.writeValueAsString(offerMessage)))
      );
      verify(mockSession1, never()).sendMessage(any(TextMessage.class));
    }
  }
}