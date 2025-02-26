package com.sprint.mission.discodeit.apidocs;

import com.sprint.mission.discodeit.dto.login.LoginDto;
import com.sprint.mission.discodeit.dto.login.LoginResponseDto;
import com.sprint.mission.discodeit.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Auth API")
public interface AuthApiDocs {

  @Operation(summary = "로그인")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "로그인 성공",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
      @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음"),
      @ApiResponse(responseCode = "400", description = "비밀번호가 일치하지 않음")
  })
  public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto);
}
