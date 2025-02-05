package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelService channelService;
    private final UserService userService;

    public BasicMessageService(MessageRepository messageRepository,ChannelService channelService,UserService userService) {
        this.messageRepository = messageRepository;
        this.channelService = channelService;
        this.userService = userService;
    }

    @Override
    public Message create(String content, UUID channelId, UUID userId) {
        Channel channel = channelService.findById(channelId);
        User user = userService.findById(userId);
        if(channel == null) {
            throw new NoSuchElementException("Channel not found with id");
        }
        if(user == null) {
            throw new NoSuchElementException("User not found with id");
        }
        Message message = new Message(content, channelId, userId);
        return messageRepository.create(message);
    }

    @Override
    public Message findById(UUID messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(()->new NoSuchElementException("Message not found with id: " + messageId));
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message update(UUID messageId, String newContent) {
        Message message = messageRepository.findById(messageId)
                        .orElseThrow(()->new NoSuchElementException("Message not found with id: " + messageId));
        message.updateContent(newContent);
        return messageRepository.create(message);
    }

    @Override
    public void delete(UUID messageId) {
        Message message = findById(messageId);
        if (message == null) throw new NoSuchElementException("Message not found with ID: " + messageId);
        messageRepository.delete(messageId);
    }
}
