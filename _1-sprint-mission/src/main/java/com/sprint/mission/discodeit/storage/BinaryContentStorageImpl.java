package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentDto;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BinaryContentStorageImpl implements BinaryContentStorage {

  private static final String STORAGE_PATH = "binaryContent"; //저장 폴더(binaryContent/uuid 로 저장)

  public BinaryContentStorageImpl() {
    File directory = new File(STORAGE_PATH);
    if (!directory.exists()) {
      directory.mkdirs();
    }
  }

  //저장
  @Override
  public UUID put(UUID id, byte[] bytes) {
    try {
      Path filePath = Paths.get(STORAGE_PATH, id.toString()); // binaryContent/{UUID} 파일 경로를 생성
      Files.write(filePath, bytes); // 파일 저장
      return id;
    } catch (Exception e) {
      throw new RuntimeException("파일 저장 실패", e);
    }
  }

  //읽기
  @Override
  public InputStream get(UUID id) {
    try {
      Path filePath = Path.of(STORAGE_PATH, id.toString()); // binaryContent/{UUID} 경로에서 파일 찾음
      return Files.newInputStream(filePath,
          StandardOpenOption.READ); // 파일을 읽기전용으로 InputStream으로 읽어 반환
    } catch (Exception e) {
      throw new RuntimeException("파일 읽기 실패", e);
    }
  }

  //다운로드
  @Override
  public ResponseEntity<?> download(BinaryContentDto binaryContentDto) {
    try {
      Path filePath = Path.of(STORAGE_PATH, binaryContentDto.id().toString());
      byte[] fileBytes = Files.readAllBytes(filePath); // 파일을 바이트 배열(byte[])로 변환

      return ResponseEntity.ok()
          .header("Content-Disposition",
              "attachment; filename=\"" + binaryContentDto.fileName() + "\"")
          .body(fileBytes);
    } catch (Exception e) {
      throw new RuntimeException("파일 다운로드 실패", e);
    }
  }
}
