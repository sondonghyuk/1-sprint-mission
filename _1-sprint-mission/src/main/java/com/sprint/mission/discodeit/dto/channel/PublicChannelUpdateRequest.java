package com.sprint.mission.discodeit.dto.channel;

public record PublicChannelUpdateRequest(
    String newChannelName,
    String newDescription
) {

}
