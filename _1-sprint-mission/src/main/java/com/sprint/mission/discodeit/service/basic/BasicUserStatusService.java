package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusCreateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {

  private final UserStatusRepository userStatusRepository;
  private final UserRepository userRepository;

  @Override
  public UserStatus create(UserStatusCreateRequest userStatusCreateRequest) {
    //User 존재하지 않으면 예외 발생
    if (!userRepository.existsById(userStatusCreateRequest.userId())) {
      throw new NoSuchElementException(
          "User with id " + userStatusCreateRequest.userId() + " does not exist");
    }
    if (userStatusRepository.findByUserId(userStatusCreateRequest.userId()).isPresent()) {
      throw new IllegalArgumentException(
          "UserStatus with id " + userStatusCreateRequest.userId() + " already exists");
    }
    User user = userRepository.findById(userStatusCreateRequest.userId())
        .orElseThrow(() -> new NoSuchElementException(
            "User with id " + userStatusCreateRequest.userId() + " not found"));

    UserStatus userStatus = new UserStatus(user,
        userStatusCreateRequest.lastActiveAt());
    return userStatusRepository.save(userStatus);
  }

  @Override
  public UserStatus findById(UUID userStatusId) {
    return userStatusRepository.findById(userStatusId)
        .orElseThrow(
            () -> new NoSuchElementException("UserStatus with id " + userStatusId + " not found"));
  }

  @Override
  public List<UserStatus> findAll() {
    return userStatusRepository.findAll().stream().toList();
  }


  @Override
  public UserStatus update(UUID userStatusId, UserStatusUpdateRequest userStatusUpdateRequest) {
    UserStatus userStatus = userStatusRepository.findById(userStatusId)
        .orElseThrow(
            () -> new NoSuchElementException("UserStatus with id " + userStatusId + " not found"));

    userStatus.updateLastActiveAt(userStatus.getLastActiveAt());

    return userStatusRepository.save(userStatus);

  }

  @Override
  public UserStatusDto updateByUserId(UUID userId,
      UserStatusUpdateRequest userStatusUpdateRequest) {
    UserStatus userStatus = userStatusRepository.findByUserId(userId)
        .orElseThrow(
            () -> new NoSuchElementException("UserStatus with userId " + userId + " not found"));
    userStatus.updateLastActiveAt(userStatus.getLastActiveAt());

    UserStatus savedUserStatus = userStatusRepository.save(userStatus);
    return this.toDto(savedUserStatus);
  }

  @Override
  public void delete(UUID userStatusId) {
    if (!userStatusRepository.existsById(userStatusId)) {
      throw new NoSuchElementException("UserStatus with id " + userStatusId + " not found");
    }
    userStatusRepository.deleteById(userStatusId);
  }

  @Override
  public UserStatusDto toDto(UserStatus userStatus) {
    return new UserStatusDto(
        userStatus.getId(),
        userStatus.getUser().getId(),
        userStatus.getLastActiveAt()
    );
  }
}
