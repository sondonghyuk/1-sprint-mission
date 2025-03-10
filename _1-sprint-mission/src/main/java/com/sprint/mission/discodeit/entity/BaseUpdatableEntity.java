package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public abstract class BaseUpdatableEntity extends Base implements Serializable {

  private static final long serialVersionUID = 1L; //직렬화 버전

  Instant updatedAt;

  public BaseUpdatableEntity() {
    super(UUID.randomUUID(), Instant.now());
  }

  //update 메소드
  public void updateTimestamp() {
    this.updatedAt = Instant.now();
  }

}
