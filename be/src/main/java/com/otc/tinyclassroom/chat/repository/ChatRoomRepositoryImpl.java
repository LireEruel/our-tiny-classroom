package com.otc.tinyclassroom.chat.repository;

import static com.otc.tinyclassroom.chat.entity.QChatMessage.chatMessage;
import static com.otc.tinyclassroom.chat.entity.QChatRoom.chatRoom;
import static com.otc.tinyclassroom.chat.entity.QChatRoomMember.chatRoomMember;

import com.otc.tinyclassroom.chat.dto.ChatRoomDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * ChatRoomRepository 구현체.
 */
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChatRoomDto> findAllChatRoomByMemberId(Long userId) {

        return jpaQueryFactory
                .select(Projections.constructor(
                        ChatRoomDto.class,
                        chatRoom.id,
                        chatMessage.id,
                        chatMessage.message
                ))
                .from(chatRoomMember)
                .join(chatRoomMember.chatRoom, chatRoom)
                .leftJoin(chatRoom.lastChatMessage, chatMessage)
                .where(chatRoomMember.member.id.eq(userId))
                .fetch();
    }

    @Override
    public ChatRoomDto findChatRoomDetail(String roomId) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        ChatRoomDto.class,
                        chatRoom.id,
                        chatMessage.id,
                        chatMessage.message
                ))
                .from(chatRoomMember)
                .join(chatRoomMember.chatRoom, chatRoom)
                .leftJoin(chatRoom.lastChatMessage, chatMessage)
                .where(chatRoom.id.eq(roomId))
                .fetchFirst();
    }
}
