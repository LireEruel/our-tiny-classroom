package com.otc.tinyclassroom.chat.repository;

import static com.otc.tinyclassroom.chat.entity.QChatRoomMember.chatRoomMember;
import static com.otc.tinyclassroom.member.entity.QMember.member;

import com.otc.tinyclassroom.chat.dto.ChatRoomMemberDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * ChatRoomMemberRepository 구현체.
 */
@RequiredArgsConstructor
public class ChatRoomMemberRepositoryImpl implements ChatRoomMemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChatRoomMemberDto> findAllRoomMemberByRoomId(String roomId) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        ChatRoomMemberDto.class,
                        member.id,
                        member.memberId,
                        member.name,
                        member.profileUrl
                ))
                .from(chatRoomMember)
                .join(chatRoomMember.member, member)
                .where(chatRoomMember.chatRoom.id.eq(roomId))
                .fetch();
    }
}
