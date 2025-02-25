package com.sprint.mission.discodeit.apidocs;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelCreateDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelUpdateDto;
import com.sprint.mission.discodeit.entity.Channel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Channel", description = "Channel API")
public interface ChannelApiDocs {

  @Operation(summary = "Public Channel 생성")
  @ApiResponse(responseCode = "201", description = "Public Channel이 성공적으로 생성됨")
  ResponseEntity<Channel> createPublic(@Valid @RequestBody PublicChannelCreateDto publicChannelDto);

  @Operation(summary = "Private Channel 생성")
  @ApiResponse(responseCode = "201", description = "Private Channel이 성공적으로 생성됨")
  ResponseEntity<Channel> createPrivate(
      @Valid @RequestBody PrivateChannelCreateDto privateChannelDto);

  @Operation(summary = "User가 참여 중인 Channel 목록 조회")
  @ApiResponse(responseCode = "200", description = "Channel 목록 조회 성공")
  ResponseEntity<List<ChannelDto>> findAll(@RequestParam(value = "userId") UUID userId);

  @Operation(summary = "Channel 정보 수정")
  @ApiResponses({
      @ApiResponse(responseCode = "404", description = "Channel을 찾을 수 없음"),
      @ApiResponse(responseCode = "400", description = "Private Channel은 수정할 수 없음"),
      @ApiResponse(responseCode = "204", description = "Channel이 성공적으로 수정됨")
  })
  ResponseEntity<Channel> update(@PathVariable UUID channelId,
      @Valid @RequestBody PublicChannelUpdateDto publicChannelUpdateDto);


  @Operation(summary = "Channel 삭제")
  @ApiResponses({
      @ApiResponse(responseCode = "404", description = "Channel을 찾을 수 없음"),
      @ApiResponse(responseCode = "204", description = "Channel이 성공적으로 삭제됨")
  })
  ResponseEntity<Void> delete(@PathVariable UUID channelId);

}
