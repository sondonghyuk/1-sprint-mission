package com.sprint.mission.discodeit.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
    @NotNull
    @Size(min = 1, max = 20)
    String username,

    @Email
    @NotNull
    String email,

    @NotNull
    @Size(min = 8)
    String password
) {

}
