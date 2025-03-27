package com.sprint.mission.discodeit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class BaseUpdatableEntity extends BaseEntity {

  @LastModifiedDate
  @Column(columnDefinition = "timestamp with time zone")
  private Instant updatedAt;

  //update 메소드
  public void updateTimestamp() {
    this.updatedAt = Instant.now();
  }

}
