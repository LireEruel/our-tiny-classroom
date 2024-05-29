package com.otc.tinyclassroom.schedule.dto;

import com.otc.tinyclassroom.lecture.dto.LectureDto;
import com.otc.tinyclassroom.member.dto.MemberDto;
import com.otc.tinyclassroom.schedule.entity.Schedule;
import java.time.LocalDate;

/**
 * 스케줄 DTO.
 */
public record ScheduleDto(Long id, MemberDto memberDto, LectureDto lectureDto, LocalDate scheduleDate, int dayOfWeek, int timeTable, Boolean deletable) {

    public static ScheduleDto of(Long id, MemberDto memberDto, LectureDto lectureDto, LocalDate scheduleDate, int dayOfWeek, int timeTable, Boolean deletable) {
        return new ScheduleDto(id, memberDto, lectureDto, scheduleDate, dayOfWeek, timeTable, deletable);
    }

    /**
     * Schedule entity -> Schedule Dto.
     */
    public static ScheduleDto from(Schedule entity) {
        return ScheduleDto.of(
            entity.getId(),
            MemberDto.from(entity.getMember()),
            LectureDto.from(entity.getLecture()),
            entity.getScheduleDate(),
            entity.getDayOfWeek(),
            entity.getTimeTable(),
            entity.getDeletable()
        );
    }

    /**
     * Schedule Dto -> Schedule entity.
     */
    public static Schedule toEntity(ScheduleDto dto) {
        return Schedule.of(
            MemberDto.toEntity(dto.memberDto),
            LectureDto.toEntity(dto.lectureDto),
            dto.scheduleDate,
            dto.dayOfWeek,
            dto.timeTable,
            dto.deletable);
    }
}
