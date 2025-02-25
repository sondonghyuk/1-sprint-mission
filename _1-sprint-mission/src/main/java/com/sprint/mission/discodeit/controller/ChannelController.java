package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelCreateDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelUpdateDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channeㄴ")
@RequiredArgsConstructor
@Tag(name = "Channel", description = "Channel API")
public class ChannelController {

  private final ChannelService channelService;

  //공개 채널 생성
  @PostMapping("/public")
  @Tag(name = "Channel")
  @Operation(summary = "Public Channel 생성")
  public ResponseEntity<Channel> createPublic(
      @Valid @RequestBody PublicChannelCreateDto publicChannelDto) {
    Channel publicChannel = channelService.create(publicChannelDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(publicChannel);
  }

  //비공개 채널 생성
  @PostMapping("/private")
  @Tag(name = "Channel")
  @Operation(summary = "Private Channel 생성")
  public ResponseEntity<Channel> createPrivate(
      @Valid @RequestBody PrivateChannelCreateDto privateChannelDto) {
    Channel privateChannel = channelService.create(privateChannelDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(privateChannel);
  }

  //공개 채널 정보 수정
  @PatchMapping("/{channelId}")
  @Tag(name = "Channel")
  @Operation(summary = "Channel 정보 수정")
  public ResponseEntity<Channel> update(@PathVariable UUID channelId,
      @Valid @RequestBody PublicChannelUpdateDto publicChannelUpdateDto) {
    Channel updatedChannel = channelService.update(channelId, publicChannelUpdateDto);
    return ResponseEntity.status(HttpStatus.OK).body(updatedChannel);
  }

  //채널 삭제
  @DeleteMapping("/{channelId}")
  @Tag(name = "Channel")
  @Operation(summary = "Channel 삭제")
  public ResponseEntity<Void> delete(@PathVariable UUID channelId) {
    channelService.deleteById(channelId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  //채널 목록 조회
  @GetMapping()
  @Tag(name = "Channel")
  @Operation(summary = "User가 참여 중인 Channel 목록 조회")
  public ResponseEntity<List<ChannelDto>> findAll(@RequestParam(value = "userId") UUID userId) {
    List<ChannelDto> channels = channelService.findAllByUserId(userId);
    return ResponseEntity.status(HttpStatus.OK).body(channels);
  }
}
