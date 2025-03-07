package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JCFReadStatusRepository implements ReadStatusRepository {

  private final Map<UUID, ReadStatus> readStatusData;

  public JCFReadStatusRepository() {
    readStatusData = new HashMap<>();
  }

  @Override
  public ReadStatus save(ReadStatus readStatus) {
    this.readStatusData.put(readStatus.getId(), readStatus);
    return readStatus;
  }

  @Override
  public Optional<ReadStatus> findById(UUID readStatusId) {
    return Optional.ofNullable(this.readStatusData.get(readStatusId));
  }

  @Override
  public List<ReadStatus> findAllByUserId(UUID userId) {
    return this.readStatusData.values().stream()
        .filter(rs -> rs.getUserId().equals(userId))
        .toList();
  }

  @Override
  public List<ReadStatus> findAllByChannelId(UUID channelId) {
    return this.readStatusData.values().stream()
        .filter(rs -> rs.getChannelId().equals(channelId))
        .toList();
  }

  @Override
  public boolean existsById(UUID id) {
    return this.readStatusData.containsKey(id);
  }

  @Override
  public void deleteById(UUID id) {
    this.readStatusData.remove(id);
  }

  @Override
  public void deleteAllByChannelId(UUID channelId) {
    this.findAllByChannelId(channelId).forEach(readStatus -> this.deleteById(readStatus.getId()));
  }


}
