package com.otc.tinyclassroom.schedule.repository;

import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import java.util.List;
import org.springframework.data.repository.query.Param;

/**
 * Querydsl을 위한 MemberScheduleRepository interface.
 */
public interface MemberScheduleRepositoryCustom {

    List<LectureCategoryType> findDistinctLectureCategoryTypeByMemberIdOrderByLectureCategoryTypeDesc(@Param("memberId") Long memberId);

    List<Long> countByMemberIdGroupByLectureCategoryType(@Param("memberId") Long memberId);
}
