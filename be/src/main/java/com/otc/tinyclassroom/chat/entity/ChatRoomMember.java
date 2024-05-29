package com.otc.tinyclassroom.chat.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.otc.tinyclassroom.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * Chat과 Member의 매핑을 위한 테이블.
 */
@Getter
@Entity
@Table
public class ChatRoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Member member;

    @ManyToOne(fetch = LAZY)
    private ChatRoom chatRoom;

    protected ChatRoomMember() {
    }

    private ChatRoomMember(Member member, ChatRoom chatRoom) {
        this.member = member;
        this.chatRoom = chatRoom;
    }

    public static ChatRoomMember of(Member member, ChatRoom chatRoom) {
        return new ChatRoomMember(member, chatRoom);
    }
}
