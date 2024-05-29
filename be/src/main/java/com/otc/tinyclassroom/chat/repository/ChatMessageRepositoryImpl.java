package com.otc.tinyclassroom.chat.repository;

import static com.otc.tinyclassroom.chat.entity.QChatMessage.chatMessage;
import static com.otc.tinyclassroom.chat.entity.QChatRoom.chatRoom;
import static com.otc.tinyclassroom.member.entity.QMember.member;

import com.otc.tinyclassroom.chat.dto.response.ChatMessageResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * ChatMessageRepository 구현체.
 */
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChatMessageResponseDto> findAllChatByRoomId(String roomId) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        ChatMessageResponseDto.class,
                        chatMessage.id,
                        chatRoom.id,
                        member.memberId,
                        member.name,
                        chatMessage.message,
                        chatMessage.createdAt,
                        chatMessage.chatMessageType
                ))
                .from(chatMessage)
                .leftJoin(chatMessage.chatRoom, chatRoom)
                .leftJoin(chatMessage.member, member)
                .where(chatRoom.id.eq(roomId))
                .orderBy(chatMessage.createdAt.asc())
                .limit(100)
                .fetch();
    }
}
