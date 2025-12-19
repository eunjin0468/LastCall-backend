package org.example.lastcall.common.config;

import lombok.RequiredArgsConstructor;
import org.example.lastcall.domain.streaming.handler.AuctionStreamingHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

  private static final String SIGNALING_URL = "/signal";
  private static final String ALL = "*";
  private final AuctionStreamingHandler auctionStreamingHandler;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(auctionStreamingHandler, SIGNALING_URL)
        .setAllowedOrigins(ALL);
  }
}