package com.sprint.mission.discodeit.dto.channel;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PublicChannelCreateRequest(
    @NotNull
    String name,

    @NotNull
    @Size(max = 100)
    String description
) {

}
