package com.otc.tinyclassroom.schedule.repository;
import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

import static com.otc.tinyclassroom.schedule.entity.QMemberSchedule.memberSchedule;

public class MemberScheduleRepositoryImpl implements MemberScheduleRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LectureCategoryType> findDistinctLectureCategoryTypeByMemberIdOrderByLectureCategoryTypeDesc(Long memberId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectDistinct(memberSchedule.lectureCategoryType)
            .from(memberSchedule)
            .where(memberSchedule.member.id.eq(memberId))
            .orderBy(memberSchedule.lectureCategoryType.desc())
            .fetch();
    }

    @Override
    public List<Long> countByMemberIdGroupByLectureCategoryType(Long memberId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.select(memberSchedule.count())
            .from(memberSchedule)
            .where(memberSchedule.member.id.eq(memberId))
            .groupBy(memberSchedule.member.id, memberSchedule.lectureCategoryType)
            .fetch();
    }
}
