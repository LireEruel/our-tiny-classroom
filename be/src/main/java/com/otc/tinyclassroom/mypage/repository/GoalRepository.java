package com.otc.tinyclassroom.mypage.repository;

import com.otc.tinyclassroom.mypage.entity.Goal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 목표 저장 Repository.
 */
public interface GoalRepository extends JpaRepository<Goal, Long> {
    Optional<Goal> findByMemberIdAndYearAndWeek(Long memberId, int year, int week);

}
