package org.example.lastcall.domain.streaming.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ActiveUser {

  private String name;

  @Builder
  private ActiveUser(String name) {
    this.name = name;
  }

  public static ActiveUser from(final UserSession userSession) {
    return ActiveUser.builder()
        .name(userSession.getName())
        .build();
  }
}
