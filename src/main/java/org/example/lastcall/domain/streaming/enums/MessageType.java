package org.example.lastcall.domain.streaming.enums;

public enum MessageType {
  JOIN,                   // 웹소켓 첫 접속 (유저 등록)
  ENTER_AUCTION_ROOM,     // 경매 방송 방 입장
  LEAVE_AUCTION_ROOM,     // 경매 방송 방 퇴장
  START_STREAM,           // 스트리밍 시작 (호스트)
  STOP_STREAM,            // 스트리밍 종료
  SEND_CHAT,              // 실시간 채팅
  SEND_BID,               // 실시간 입찰(원하면 WebRTC가 아니라 REST+WebSocket도 가능)
}
