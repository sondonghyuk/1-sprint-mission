package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public abstract class Base implements Serializable {

  private static final long serialVersionUID = 1L; //직렬화 버전
  //필드
  private UUID id;
  private Instant createdAt;

  //생성자
  public Base(UUID id, Instant createdAt) {
    this.id = id != null ? id : UUID.randomUUID();
    this.createdAt = createdAt != null ? createdAt : Instant.now();
  }
}
