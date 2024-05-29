package com.otc.tinyclassroom.member.repository;

import com.otc.tinyclassroom.member.entity.ClassRoom;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.entity.QMemberClassRoom;
import com.otc.tinyclassroom.member.entity.Role;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * custom Repository 구현.
 */
@Repository
@AllArgsConstructor
@SuppressWarnings("checkstyle:LocalVariableName")
public class MemberClassRoomRepositoryCustomImpl implements MemberClassRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Member> findMemberByClassRoomIdAndRole(Long classRoomId, Role role) {
        QMemberClassRoom qMemberClassRoom = QMemberClassRoom.memberClassRoom;

        return queryFactory
            .select(qMemberClassRoom.member)
            .from(qMemberClassRoom)
            .where(qMemberClassRoom.classRoom.id.eq(classRoomId)
                .and(qMemberClassRoom.member.role.eq(role)))
            .fetch();
    }

    @Override
    public List<Member> findMemberByClassRoomId(Long classRoomId) {
        QMemberClassRoom qMemberClassRoom = QMemberClassRoom.memberClassRoom;

        return queryFactory
            .select(qMemberClassRoom.member)
            .from(qMemberClassRoom)
            .where(qMemberClassRoom.classRoom.id.eq(classRoomId))
            .fetch();
    }

    @Override
    public List<ClassRoom> findClassRoomByMemberId(Long memberId) {
        QMemberClassRoom qMemberClassRoom = QMemberClassRoom.memberClassRoom;
        return queryFactory
            .select(qMemberClassRoom.classRoom)
            .from(qMemberClassRoom)
            .where(qMemberClassRoom.member.id.eq(memberId))
            .fetch();

    }
}
