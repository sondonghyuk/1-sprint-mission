package com.sprint.mission.discodeit.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "Message 생성 정보")
public record MessageCreateRequest(
    @NotNull
    String content,

    @NotNull
    UUID authorId,

    @NotNull
    UUID channelId
) {

}
