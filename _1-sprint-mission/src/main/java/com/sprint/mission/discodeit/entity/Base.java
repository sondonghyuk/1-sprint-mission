package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

@Getter
public abstract class Base implements Serializable {

  private static final long serialVersionUID = 1L; //직렬화 버전
  //필드
  private UUID id;

  @CreatedDate
  private Instant createdAt;

  //생성자
  public Base() {
    this.id = UUID.randomUUID();
    this.createdAt = Instant.now();
  }
}
