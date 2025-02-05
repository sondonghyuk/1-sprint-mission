package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;

    public BasicChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Channel create(Channel.ChannelType channelType, String channelName, String description) {
        Channel channel = new Channel(channelType, channelName, description);
        return channelRepository.create(channel);
    }

    @Override
    public Channel findById(UUID channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(()-> new NoSuchElementException("ChannelId not found"));
    }

    @Override
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public Channel update(UUID channelId, String field,Object value) {
        Channel channel = channelRepository.findById(channelId)
                        .orElseThrow(()-> new NoSuchElementException("ChannelId not found"));
            switch (field) {
                case "channelName":
                    channel.setChannelName((String) value);
                    break;
                case "description":
                    channel.setDescription((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + field);
            }
            return channelRepository.create(channel);
    }

    @Override
    public void delete(UUID channelId) {
        channelRepository.delete(channelId);
    }
}
