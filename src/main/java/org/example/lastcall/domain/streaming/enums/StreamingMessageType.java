package org.example.lastcall.domain.streaming.enums;

public enum StreamingMessageType {
  JOIN_ROOM,          // 방송 방 입장
  LEAVE_ROOM,         // 방송 방 퇴장
  START_BROADCAST,    // 방송 시작
  STOP_BROADCAST,     // 방송 종료
  OFFER,              // WebRTC SDP offer
  ANSWER,             // WebRTC SDP answer
  ICE_CANDIDATE       // WebRTC ICE candidate
}
