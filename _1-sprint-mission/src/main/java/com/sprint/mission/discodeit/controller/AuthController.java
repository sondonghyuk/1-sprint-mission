package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.apidocs.AuthApiDocs;
import com.sprint.mission.discodeit.dto.login.LoginDto;
import com.sprint.mission.discodeit.dto.login.LoginResponseDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApiDocs {

  private final AuthService authService;

  @PostMapping("/login")
  @Override
  public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
    User loginUser = authService.login(loginDto);
    LoginResponseDto response = new LoginResponseDto(
        loginUser.getUsername(),
        loginUser.getEmail(),
        loginUser.getAddress(),
        loginUser.getAddress());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
