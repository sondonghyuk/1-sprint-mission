package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Channel extends Common implements Serializable {
    private static final long serialVersionUID = 1L; //직렬화 버전

    //필드
    public enum ChannelType {
        PUBLIC,PRIVATE
    } //채널 타입
    private ChannelType channelType;
    private String channelName; // 채널 이름
    private String description; // 채널 설명

    // PUBLIC 전용 생성자
    public Channel(ChannelType channelType,String channelName, String description) {
        super(UUID.randomUUID(), Instant.now());
        //검증
        if (channelType == null) {
            throw new IllegalArgumentException("채널 타입은 필수입니다.(PUBLIC,PRIVATE)");
        }
        if (channelName == null || channelName.trim().isEmpty() || channelName.length() > 30) {
            throw new IllegalArgumentException("채널 이름은 1~30자 사이여야 합니다.");
        }
        if (description != null && description.length() > 100) {
            throw new IllegalArgumentException("채널 설명은 최대 100자까지 가능합니다.");
        }
        this.channelType = channelType;
        this.channelName = channelName;
        this.description = description;
    }
    //PRIVATE 전용 생성자
    public Channel(ChannelType channelType) {
        super(UUID.randomUUID(), Instant.now());
        //검증
        if (channelType == null) {
            throw new IllegalArgumentException("채널 타입은 필수입니다.(PUBLIC,PRIVATE)");
        }
        this.channelType = channelType;
    }
    //팩토리 메서드
    public static Channel createPublicChannel(String name,String description) {
        return new Channel(ChannelType.PUBLIC,name,description);
    }
    public static Channel createPrivateChannel() {
        return new Channel(ChannelType.PRIVATE,null,null);
    }

    @Override
    public String toString() {
        return String.format(
                "Channel{ channelType=%s , channelName=%s , description=%s}",
                channelType , channelName ,description
        );
    }

    // update 메소드
    public void updateChannel(String newChannelName, String newDescription) {
        if(newChannelName!=null && !newChannelName.trim().isEmpty() &&!newChannelName.equals(this.channelName)){
            this.channelName = newChannelName;
            updateTimestamp();
        }
        if(newDescription!=null && !newDescription.trim().isEmpty() && !newDescription.equals(this.description)){
            this.description = newDescription;
            updateTimestamp();
        }
    }

}
