package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateDto;
import com.sprint.mission.discodeit.dto.user.UserCreateDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@ResponseBody //데이터 응답
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping(path="create",consumes={MediaType.MULTIPART_FORM_DATA_VALUE}) //post 요청 , Content-Type이 multipart/form-data
    public ResponseEntity<User> create(@RequestPart("userCreateDto") UserCreateDto userCreateDto,
                                      @RequestPart(value="profile",required = false) MultipartFile profile){
        Optional<BinaryContentCreateDto> profileDto = Optional.ofNullable(profile).flatMap(this::resolveProfileDto);
        User createdUser = userService.create(userCreateDto,profileDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    private Optional<BinaryContentCreateDto> resolveProfileDto(MultipartFile profileFile) {
        if(profileFile.isEmpty()) {
            return Optional.empty();
        }
        else{
            try{
                BinaryContentCreateDto binaryContentCreateDto = new BinaryContentCreateDto(
                        profileFile.getOriginalFilename(),
                        profileFile.getContentType(),
                        profileFile.getBytes()
                );
                return Optional.of(binaryContentCreateDto);
            }catch(IOException e){
                throw new RuntimeException(e);
            }
        }
    }
}
