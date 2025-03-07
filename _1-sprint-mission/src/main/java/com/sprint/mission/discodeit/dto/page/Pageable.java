package com.sprint.mission.discodeit.dto.page;

import java.util.List;

public record Pageable(int page, int size, List<String> sort) {

  public Pageable {
    if (page < 0) {
      throw new IllegalArgumentException("page는 0 이상이어야 합니다.");
    }
    if (size < 1) {
      throw new IllegalArgumentException("size는 1 이상이어야 합니다.");
    }
  }
}
