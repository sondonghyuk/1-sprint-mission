package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.annotation.LastModifiedDate;

public abstract class BaseUpdatableEntity extends Base {

  private static final long serialVersionUID = 1L; //직렬화 버전

  @LastModifiedDate
  private Instant updatedAt;

  public BaseUpdatableEntity() {
    super();
    this.updatedAt = getCreatedAt();
  }

  //update 메소드
  public void updateTimestamp() {
    this.updatedAt = Instant.now();
  }

}
