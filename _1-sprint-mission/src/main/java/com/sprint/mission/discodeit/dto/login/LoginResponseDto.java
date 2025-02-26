package com.sprint.mission.discodeit.dto.login;

public record LoginResponseDto(
    String username,
    String email,
    String address,
    String phoneNumber
) {

}
