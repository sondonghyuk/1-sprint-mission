package com.sprint.mission.discodeit.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record UserUpdateDto(
    String newUsername,
    String newEmail,
    @JsonIgnore //JSON 응답에서 제외
    String newPassword,
    String newPhonenumber,
    String newAddress
) {

}
