package com.otc.tinyclassroom.schedule.repository;

import com.otc.tinyclassroom.schedule.entity.MemberSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Member와 Schedule 매핑테이블의 Repository.
 */
public interface MemberScheduleRepository extends JpaRepository<MemberSchedule, Long>, MemberScheduleRepositoryCustom {

    void deleteByMemberIdAndScheduleId(Long memberId, Long scheduleId);

    Long countByMemberId(Long memberId);

}