package com.sprint.mission.discodeit.dto.login;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "로그인 정보")
public record LoginRequest(
    @NotNull
    String username,

    @NotNull
    String password
) {

}
