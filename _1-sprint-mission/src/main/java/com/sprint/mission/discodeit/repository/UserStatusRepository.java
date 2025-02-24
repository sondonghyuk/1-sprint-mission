package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserStatusRepository {
    UserStatus save(UserStatus userStatus);
    Optional<UserStatus> findById(UUID userStatusId);
    Optional<UserStatus> findByUserId(UUID userId);
    List<UserStatus> findAll();
    boolean existsById(UUID userStatusId);
    void deleteById(UUID userStatusId);
    void deleteByUserId(UUID userId);
}
