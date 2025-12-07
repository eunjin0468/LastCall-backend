package org.example.lastcall.domain.streaming.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ActiveUsers {

  private List<ActiveUser> users;

  @Builder
  private ActiveUsers(List<ActiveUser> users) {
    this.users = users;
  }

  public static ActiveUsers from(final List<ActiveUser> users) {
    return ActiveUsers.builder().users(users).build();
  }
}
