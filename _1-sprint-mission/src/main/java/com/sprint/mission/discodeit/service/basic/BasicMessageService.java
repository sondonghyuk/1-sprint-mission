package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentDto;
import com.sprint.mission.discodeit.dto.message.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service // 서비스 Bean
@RequiredArgsConstructor // 생성자
public class BasicMessageService implements MessageService {

  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;
  private final BinaryContentRepository binaryContentRepository;
  private final MessageMapper messageMapper;
  private final BinaryContentStorage binaryContentStorage;

  @Transactional
  @Override
  public MessageDto create(MessageCreateRequest messageCreateRequest,
      List<BinaryContentCreateRequest> binaryContentCreateRequests) {
    UUID channelId = messageCreateRequest.channelId();
    UUID authorId = messageCreateRequest.authorId();

    validateIds(channelId, authorId);

    Channel channel = channelRepository.findById(channelId)
        .orElseThrow(NoSuchElementException::new);
    User user = userRepository.findById(authorId).orElseThrow(NoSuchElementException::new);

    List<BinaryContent> attachments = binaryContentCreateRequests.stream()
        .map(b -> {
          UUID attachmentId = UUID.randomUUID();
          binaryContentStorage.put(attachmentId, b.bytes());
          return new BinaryContent(b.fileName(), (long) b.bytes().length, b.contentType());
        })
        .toList();

    Message message = new Message(
        messageCreateRequest.content(),
        channel,
        user,
        attachments
    );
    Message savedMessage = messageRepository.save(message);
    return messageMapper.toDto(savedMessage);
  }

  @Override
  public Message findById(UUID messageId) {
    return messageRepository.findById(messageId)
        .orElseThrow(
            () -> new NoSuchElementException("Message with id " + messageId + " not found"));
  }

  @Override
  public List<Message> findAllByChannelId(UUID channelId) {
    return messageRepository.findAllByChannelId(channelId).stream().toList();
  }

  @Override
  public MessageDto update(UUID messageId, MessageUpdateRequest updateMessageDto) {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(
            () -> new NoSuchElementException("Message with id " + messageId + " not found"));
    message.updateContent(updateMessageDto.newContent());
    Message savedMessage = messageRepository.save(message);
    return messageMapper.toDto(savedMessage);
  }


  @Transactional
  @Override
  public void delete(UUID messageId) {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(
            () -> new NoSuchElementException("Message with id " + messageId + " not found"));
    //관련된 BinaryContent 삭제
    message.getAttachments()
        .forEach(binaryContent -> binaryContentRepository.deleteById(binaryContent.getId()));
    //메시지 삭제
    messageRepository.deleteById(messageId);
  }

  public void validateIds(UUID channelId, UUID authorId) {
    if (!channelRepository.existsById(channelId)) {
      throw new NoSuchElementException("Channel with id " + channelId + " does not exist");
    }
    if (!userRepository.existsById(authorId)) {
      throw new NoSuchElementException("Author with id " + authorId + " does not exist");
    }
  }
}
