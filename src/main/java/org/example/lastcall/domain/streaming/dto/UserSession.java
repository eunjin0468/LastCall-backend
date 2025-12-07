package org.example.lastcall.domain.streaming.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
public class UserSession {

  private String name;
  private WebSocketSession session;

  @Builder
  private UserSession(String name, WebSocketSession session) {
    this.name = name;
    this.session = session;
  }

  public UserSession toUserSession(final WebSocketSession session) {
    return UserSession.builder()
        .name(this.name)
        .session(session)
        .build();
  }
}