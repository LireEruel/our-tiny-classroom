package com.otc.tinyclassroom.chat.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.otc.tinyclassroom.chat.entity.type.ChatMessageType;
import com.otc.tinyclassroom.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.annotations.CurrentTimestamp;

/**
 * 채팅 메세지 엔티티.
 */
@Getter
@Entity
@Table
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = LAZY)
    private Member member;

    private String message;

    @CurrentTimestamp
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private ChatMessageType chatMessageType;

    protected ChatMessage() {

    }

    private ChatMessage(ChatRoom chatRoom, Member member, String message, ChatMessageType chatMessageType) {
        this.chatRoom = chatRoom;
        this.member = member;
        this.message = message;
        this.chatMessageType = chatMessageType;
    }

    /**
     * 팩토리 메서드 생성자.
     */
    public static ChatMessage of(ChatRoom chatRoom, Member member, String message, ChatMessageType chatMessageType) {
        return new ChatMessage(chatRoom, member, message, chatMessageType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatMessage chatMessage)) {
            return false;
        }
        return id.equals(chatMessage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
