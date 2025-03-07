package com.sprint.mission.discodeit.dto.page;

import java.util.List;

public record PageResponse(
    List<Object> content,
    int number,
    int size,
    Boolean hasNext,
    Long totalElements
) {

}
