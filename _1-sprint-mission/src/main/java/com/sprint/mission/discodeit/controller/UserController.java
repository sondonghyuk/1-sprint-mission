package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateDto;
import com.sprint.mission.discodeit.dto.user.UserCreateDto;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.UserUpdateDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusUpdateDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {

  private final UserService userService;
  private final UserStatusService userStatusService;

  //사용자 생성
  @PostMapping("/create")
  @Tag(name = "User")
  @Operation(summary = "User 등록")
  public ResponseEntity<User> create(
      @RequestPart("user") @Valid UserCreateDto userCreateDto,
      @RequestPart(value = "profile", required = false) MultipartFile profile) {
    Optional<BinaryContentCreateDto> profileDto = Optional.ofNullable(profile)
        .flatMap(this::resolveProfileDto);
    User createdUser = userService.create(userCreateDto, profileDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }

  //사용자 다건 조회
  @GetMapping("/findAll")
  @Tag(name = "User")
  @Operation(summary = "전체 User 목록 조회")
  public ResponseEntity<List<UserDto>> findAll() {
    List<UserDto> users = userService.findAll();
    return ResponseEntity.status(HttpStatus.OK).body(users);
  }

  //사용자 수정
  @PutMapping("/update/{userId}")
  @Tag(name = "User")
  @Operation(summary = "User 정보 수정")
  public ResponseEntity<User> update(
      @PathVariable UUID userId,
      @Valid @RequestPart("user") UserUpdateDto userUpdateDto,
      @Valid @RequestPart(value = "profile", required = false) MultipartFile profile) {
    Optional<BinaryContentCreateDto> profileDto = Optional.ofNullable(profile)
        .flatMap(this::resolveProfileDto);
    User updatedUser = userService.update(userId, userUpdateDto, profileDto);
    return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
  }

  //사용자 삭제
  @DeleteMapping("/delete/{userId}")
  @Tag(name = "User")
  @Operation(summary = "User 삭제")
  public ResponseEntity<Void> delete(@PathVariable UUID userId) {
    userService.deleteById(userId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //응답 데이터에 정보가 없음
  }


  //사용자 온라인 상태 업데이트
  @PutMapping("/update/status/{userId}")
  @Tag(name = "User")
  @Operation(summary = "User 온라인 상태 업데이트")
  public ResponseEntity<UserStatus> updateUserStatusByUserId(@PathVariable UUID userId,
      @Valid @RequestBody UserStatusUpdateDto status) {
    UserStatus updatedUserStatus = userStatusService.updateByUserId(userId, status);
    return ResponseEntity.status(HttpStatus.OK).body(updatedUserStatus);
  }

  //프로필 이미지 처리 메서드
  private Optional<BinaryContentCreateDto> resolveProfileDto(MultipartFile profileFile) {
    if (profileFile.isEmpty()) {
      return Optional.empty();
    } else {
      try {
        BinaryContentCreateDto binaryContentCreateDto = new BinaryContentCreateDto(
            profileFile.getOriginalFilename(),
            profileFile.getContentType(),
            profileFile.getBytes()
        );
        return Optional.of(binaryContentCreateDto);
      } catch (IOException e) {
        throw new RuntimeException("파일을 처리하는 동안 오류 발생", e);
      }
    }
  }
}
