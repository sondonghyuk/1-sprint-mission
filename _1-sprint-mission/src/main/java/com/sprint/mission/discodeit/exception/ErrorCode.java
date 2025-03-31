package com.sprint.mission.discodeit.exception;

public enum ErrorCode {
  //User
  USER_NOT_FOUND("유저를 찾을 수 없습니다."),
  USER_DUPLICATE_USERNAME("유저 USERNAME 중복입니다."),
  USER_DUPLICATE_EMAIL("유저 EMAIL 중복입니다."),

  //Channel
  CHANNEL_NOT_FOUND("채널을 찾을 수 없습니다."),
  PRIVATE_CHANNEL_CANNOT_UPDATE("PRIVATE 채널은 업데이트 할 수 없습니다."),

  //Message
  MESSAGE_NOT_FOUND("메시지를 찾을 수 없습니다."),

  //BinaryContent
  BINARYCONENT_NOT_FOUND("BINARY CONTENT를 찾을 수 없습니다.");

  private final String message;

  public String getMessage() {
    return message;
  }

  ErrorCode(String message) {
    this.message = message;
  }
}
