package com.otc.tinyclassroom.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

/**
 * 채팅룸 엔티티.
 */
@Getter
@Entity
@Table
public class ChatRoom {

    @Id
    private String id;

    @OneToOne
    private ChatMessage lastChatMessage;

    protected ChatRoom() {

    }

    private ChatRoom(String id, ChatMessage lastChatMessage) {
        this.id = id;
        this.lastChatMessage = lastChatMessage;
    }

    /**
     * 팩토리 메서드 생성자.
     */
    public static ChatRoom of() {
        String tempId = UUID.randomUUID().toString();
        return new ChatRoom(tempId, null);
    }

    public void updateLastChatMessage(ChatMessage lastChatMessage) {
        this.lastChatMessage = lastChatMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatRoom chatRoom)) {
            return false;
        }
        return id != null && id.equals(chatRoom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}