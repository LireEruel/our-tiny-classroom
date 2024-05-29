package com.otc.tinyclassroom.schedule.repository;

import com.otc.tinyclassroom.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 스케줄 레퍼지토리.
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {

}
