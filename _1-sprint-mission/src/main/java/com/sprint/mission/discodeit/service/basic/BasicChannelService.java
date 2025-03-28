package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service //서비스 Bean
@RequiredArgsConstructor // 생성자
public class BasicChannelService implements ChannelService {

  private final ChannelRepository channelRepository;
  private final ReadStatusRepository readStatusRepository;
  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final ChannelMapper channelMapper;

  @Transactional
  @Override
  public ChannelDto create(PublicChannelCreateRequest publicChannelCreateRequest) {
    Channel channel = new Channel(
        ChannelType.PUBLIC,
        publicChannelCreateRequest.name(),
        publicChannelCreateRequest.description()
    );
    channelRepository.save(channel);
    return channelMapper.toDto(channel);
  }

  @Transactional
  @Override
  public ChannelDto create(PrivateChannelCreateRequest privateChannelCreateRequest) {
    Channel channel = new Channel(ChannelType.PRIVATE, null, null);
    channelRepository.save(channel);

    List<ReadStatus> readStatuses = userRepository.findAllById(
            privateChannelCreateRequest.participantIds())
        .stream()
        .map(user -> new ReadStatus(user, channel, channel.getCreatedAt()))
        .toList();

    readStatusRepository.saveAll(readStatuses);

    return channelMapper.toDto(channel);
  }


  @Transactional(readOnly = true)
  @Override
  public ChannelDto findById(UUID channelId) {
    return channelRepository.findById(channelId)
        .map(channelMapper::toDto)
        .orElseThrow(
            () -> new NoSuchElementException("Channel with id " + channelId + " not found"));
  }

  //특정 User가 볼 수 있는 Channel 목록을 조회하도록 조회 조건을 추가
  @Transactional(readOnly = true)
  @Override
  public List<ChannelDto> findAllByUserId(UUID userId) {
    List<UUID> mySubscribedChannelIds = readStatusRepository.findAllByUserId(userId)
        .stream()
        .map(ReadStatus::getChannel)
        .map(Channel::getId)
        .toList();

    return channelRepository.findAllByTypeOrIdIn(ChannelType.PUBLIC, mySubscribedChannelIds)
        .stream()
        .map(channelMapper::toDto)
        .toList();
  }

  @Transactional
  @Override
  public ChannelDto update(UUID channelId, PublicChannelUpdateRequest channelUpdateDto) {
    Channel channel = channelRepository.findById(channelId)
        .orElseThrow(
            () -> new NoSuchElementException("Channel with id " + channelId + " not found"));

    if (channel.getType().equals(ChannelType.PRIVATE)) {
      throw new IllegalArgumentException("Private channel cannot be updated");
    }

    channel.updateChannel(channelUpdateDto.newName(), channelUpdateDto.newDescription());

    return channelMapper.toDto(channel);
  }

  @Transactional
  @Override
  public void deleteById(UUID channelId) {
    if (!channelRepository.existsById(channelId)) {
      throw new NoSuchElementException("Channel with id " + channelId + " not found");
    }

    //관련된 Message 삭제
    messageRepository.deleteAllByChannelId(channelId);

    //관련된 ReadStatus 삭제
    readStatusRepository.deleteAllByChannelId(channelId);

    //채널 삭제
    channelRepository.deleteById(channelId);

  }
}