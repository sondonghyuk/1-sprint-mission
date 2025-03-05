package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.apidocs.ReadStatusApi;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/readStatuses")
@RequiredArgsConstructor
public class ReadStatusController implements ReadStatusApi {

  private final ReadStatusService readStatusService;

  //메시지 읽음 상태 생성
  @PostMapping
  @Override
  public ResponseEntity<ReadStatus> create(@RequestBody ReadStatusCreateRequest readStatusDto) {
    ReadStatus createdReadStatus = readStatusService.create(readStatusDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdReadStatus);
  }

  //메시지 읽음 상태 목록 조회
  @GetMapping
  @Override
  public ResponseEntity<List<ReadStatus>> findAllByUserId(@RequestParam("userId") UUID userId) {
    List<ReadStatus> readStatuses = readStatusService.findAllByUserId(userId);
    return ResponseEntity.status(HttpStatus.OK).body(readStatuses);
  }


  //메시지 읽음 상태 수정
  @PatchMapping("/{readStatusId}")
  @Override
  public ResponseEntity<ReadStatus> update(@PathVariable("readStatusId") UUID readStatusId,
      @RequestBody ReadStatusUpdateRequest readStatusUpdateRequest) {
    ReadStatus updatedReadStatus = readStatusService.update(readStatusId, readStatusUpdateRequest);
    return ResponseEntity.status(HttpStatus.OK).body(updatedReadStatus);
  }


}
