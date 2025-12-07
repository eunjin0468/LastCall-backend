package org.example.lastcall.domain.streaming.dto;

import static org.example.lastcall.domain.streaming.enums.MessageType.JOIN;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Getter
@NoArgsConstructor
public class SignalingMessage {

  private String type;
  private String sender;
  private String receiver;
  private Object data;

  @Builder
  public SignalingMessage(String type, String sender, String receiver, Object data) {
    this.type = type;
    this.sender = sender;
    this.receiver = receiver;
    this.data = data;
  }

  // JOIN 응답 메시지
  public static SignalingMessage ofJoinResult(String receiver, boolean result) {
    return SignalingMessage.builder()
        .type("join")
        .receiver(receiver)
        .data(JoinResult.builder().result(result).build())
        .build();
  }

  public UserSession toUserSession(final WebSocketSession session) {
    return UserSession.builder()
        .name(sender)
        .session(session)
        .build();
  }
}
