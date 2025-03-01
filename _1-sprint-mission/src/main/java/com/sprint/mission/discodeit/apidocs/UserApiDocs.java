package com.sprint.mission.discodeit.apidocs;

import com.sprint.mission.discodeit.dto.user.UserCreateRequst;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.UserUpdateRequest;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User", description = "User API")
public interface UserApiDocs {

  @Operation(summary = "User 등록")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "User가 성공적으로 생성됨"),
      @ApiResponse(responseCode = "400", description = "같은 email 또는 username를 사용하는 User가 이미 존재함")
  })
  ResponseEntity<User> create(@RequestPart("user") @Valid UserCreateRequst userCreateRequst,
      @RequestPart(value = "profile", required = false) MultipartFile profile);

  @Operation(summary = "전체 User 목록 조회")
  @ApiResponse(responseCode = "200", description = "User 목록 조회 성공")
  ResponseEntity<List<UserDto>> findAll();


  @Operation(summary = "User 정보 수정")
  @ApiResponses({
      @ApiResponse(responseCode = "404", description = "User를 찾을 수 없음"),
      @ApiResponse(responseCode = "400", description = "같은 email 또는 username를 사용하는 User가 이미 존재함"),
      @ApiResponse(responseCode = "200", description = "User 정보가 성공적으로 수정됨")
  })
  ResponseEntity<User> update(@PathVariable UUID userId,
      @Valid @RequestPart("user") UserUpdateRequest userUpdateRequest,
      @Valid @RequestPart(value = "profile", required = false) MultipartFile profile);

  @Operation(summary = "User 온라인 상태 업데이트")
  @ApiResponses({
      @ApiResponse(responseCode = "404", description = "해당 User의 UserStatus를 찾을 수 없음"),
      @ApiResponse(responseCode = "200", description = "User 온라인 상태가 성공적으로 업데이트됨")
  })
  ResponseEntity<UserStatus> updateUserStatusByUserId(@PathVariable UUID userId,
      @Valid @RequestBody UserStatusUpdateRequest status);

  @Operation(summary = "User 삭제")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "User가 성공적으로 삭제됨"),
      @ApiResponse(responseCode = "404", description = "User를 찾을 수 없음")
  })
  ResponseEntity<Void> delete(@PathVariable UUID userId);

}
