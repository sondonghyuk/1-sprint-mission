package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel create(Channel.ChannelType channelType,String channelName, String description); //채널 생성
    Channel findById(UUID channelId); // 채널 단일 조회
    List<Channel> findAll(); // 모든 채널 조회
    Channel update(UUID channelId,String field,Object value); // 채널 수정
    void delete(UUID channelId); // 채널 삭제
}
