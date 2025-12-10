package org.example.lastcall.domain.streaming.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinResult {
  private boolean result;

  @Builder
  private JoinResult(boolean result) {
    this.result = result;
  }
}
