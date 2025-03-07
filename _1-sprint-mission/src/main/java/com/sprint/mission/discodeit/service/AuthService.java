package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.login.LoginRequest;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.entity.User;

public interface AuthService {

  UserDto login(LoginRequest loginRequest);
}
