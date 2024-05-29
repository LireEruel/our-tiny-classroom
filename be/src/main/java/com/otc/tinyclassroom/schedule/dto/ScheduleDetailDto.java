package com.otc.tinyclassroom.schedule.dto;

import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import com.otc.tinyclassroom.lecture.entity.type.LectureType;
import java.time.LocalDate;

/**
 * 시간표의 상세한 데이터를 전달하기 위한 Dto.
 */
public record ScheduleDetailDto(Long lectureId, String title, LectureType lectureType,
                                LectureCategoryType lectureCategoryType, LocalDate scheduleDate, int dayOfWeek,
                                int timeTable, String lectureUrl) {

    public static ScheduleDetailDto of(Long lectureId, String title, LectureType lectureType,
        LectureCategoryType lectureCategoryType, LocalDate scheduleDate, int dayOfWeek, int timeTable, String lectureUrl) {
        return new ScheduleDetailDto(lectureId, title, lectureType, lectureCategoryType, scheduleDate, dayOfWeek, timeTable, lectureUrl);
    }

}