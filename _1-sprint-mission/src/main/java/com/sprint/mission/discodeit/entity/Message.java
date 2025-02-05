package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class Message extends Common implements Serializable {
    private static final long serialVersionUID = 1L; //직렬화 버전
    //필드
    private String content; //메세지내용
    private UUID channelId; // 채널 id
    private UUID userId; // 작성자 id

    //생성자
    public Message(String content, UUID channelId, UUID userId) {
        super();
        // 검증
        if (content == null || content.trim().isEmpty() || content.length() > 500) {
            throw new IllegalArgumentException("메시지 내용은 1~500자 사이여야 합니다.");
        }
        if (channelId == null) {
            throw new IllegalArgumentException("채널 ID는 필수입니다.");
        }
        if (userId == null) {
            throw new IllegalArgumentException("작성자 ID는 필수입니다.");
        }

        this.content = content;
        this.channelId = channelId;
        this.userId = userId;
    }

    //Getter
    public String getContent() {
        return content;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public UUID getUserId() {
        return userId;
    }
    //Setter
    public void setContent(String content) {
        this.content = content;
    }

    public void setChannelId(UUID channelId) {
        this.channelId = channelId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return String.format(
                "Message { messageId=%s, content='%s', channelId=%s, userId=%s }",
                getId(), content, channelId, userId
        );
    }

    // update 메소드
    public void updateContent(String newContent) {
        if (newContent != null && !newContent.trim().isEmpty() && !newContent.equals(this.content)) {
            this.content = newContent;
            updateTimestamp();
        }
    }
}
