package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusUpdateDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusCreateDto;
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

    public UserStatus create(UserStatusCreateDto userStatusCreateDto) {
        //User 존재하지 않으면 예외 발생
        if(!userRepository.existsById(userStatusCreateDto.userId())){
            throw new NoSuchElementException("User with id " + userStatusCreateDto.userId() + " does not exist");
        }
        //User와 관련된 객체가 이미 존재하면 예외 발생
        Optional<UserStatus> exist = userStatusRepository.findById(userStatusCreateDto.userId());
        if(exist.isPresent()){
            throw new IllegalArgumentException("UserStatus with id " + userStatusCreateDto.userId() + " already exists");
        }

        UserStatus userStatus = new UserStatus(userStatusCreateDto.userId(),userStatusCreateDto.lastConnetTime());
        return userStatusRepository.save(userStatus);
    }

    public UserStatus findById(UUID userStatusId) {
        return userStatusRepository.findById(userStatusId)
                .orElseThrow(() -> new NoSuchElementException("UserStatus with id " + userStatusId + " not found"));
    }

    public List<UserStatus> findAll(){
        return userStatusRepository.findAll().stream().toList();
    }

    public UserStatus update(UUID userStatusId,UserStatusUpdateDto userStatusUpdateDto){
        UserStatus userStatus = userStatusRepository.findById(userStatusId)
                .orElseThrow(() -> new NoSuchElementException("UserStatus with id " + userStatusId + " not found"));

        userStatus.updateLastConnectTime();

        return userStatusRepository.save(userStatus);

    }

    public UserStatus updateByUserId(UUID userId,UserStatusUpdateDto userStatusUpdateDto){
        UserStatus userStatus = userStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("UserStatus with userId " + userId + " not found"));
        userStatus.updateLastConnectTime();

        return userStatusRepository.save(userStatus);
    }

    public void delete(UUID userStatusId){
        if(!userStatusRepository.existsById(userStatusId)){
            throw new NoSuchElementException("UserStatus with id " + userStatusId + " not found");
        }
        userStatusRepository.deleteById(userStatusId);
    }
}
