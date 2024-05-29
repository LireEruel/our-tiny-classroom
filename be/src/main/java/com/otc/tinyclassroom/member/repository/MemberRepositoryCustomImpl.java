package com.otc.tinyclassroom.member.repository;

import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Member> findMemberByMemberId(String memberId) {
        QMember member = QMember.member;
        Member result = queryFactory
            .selectFrom(member)
            .where(member.memberId.eq(memberId))
            .fetchOne();
        return Optional.ofNullable(result);
    }
}
