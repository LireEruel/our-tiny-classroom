package com.otc.tinyclassroom.attendance.repository;

import com.otc.tinyclassroom.attendance.entity.Attendance;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 출석 레포지토리.
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /**
     * 해당 회원이 특정 시간 사이에 등교한 이력이 있는지 확인.
     */
    boolean existsByMemberIdAndCheckInBetween(Long memberId, LocalDateTime checkIn, LocalDateTime checkIn2);

    /**
     * 멤버 ID를 통해 조회하고 범위 내 시간 확인.
     */
    Optional<Attendance> findByMemberIdAndCheckInBetween(
        Long memberId,
        LocalDateTime startTime,
        LocalDateTime endTime
    );

    /**
     * 쿼리 어노테이션을 통해 파라미터 로 받은 값들을 Attendance 엔티티 조회를 통해 비교한다. `
     */
    // Todo : Query DSL로 수정
    @Query("SELECT a FROM Attendance a WHERE a.member.memberId = :memberId AND a.checkIn BETWEEN :startOfMonth AND :endOfMonth")
    List<Attendance> findByMemberIdAndCheckInBetweenMonth(
        @Param("memberId") String memberId,
        @Param("startOfMonth") LocalDateTime startOfMonth,
        @Param("endOfMonth") LocalDateTime endOfMonth
    );

    @Query("SELECT a FROM Attendance a WHERE a.member.memberId = :memberId AND a.checkIn BETWEEN :startOfDay AND :endOfDay")
    List<Attendance> findByMemberIdAndCheckInBetweenDay(
        @Param("memberId") String memberId,
        @Param("startOfDay") LocalDateTime startOfDay,
        @Param("endOfDay") LocalDateTime endOfDay
    );

}
