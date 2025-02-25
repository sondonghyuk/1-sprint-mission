package com.sprint.mission.discodeit.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record UserUpdateDto(
    String newUsername,
    String newEmail,
    String newPassword,
    String newPhonenumber,
    String newAddress
) {

}
