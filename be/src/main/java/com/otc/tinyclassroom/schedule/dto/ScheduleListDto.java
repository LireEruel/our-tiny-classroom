package com.otc.tinyclassroom.schedule.dto;

import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import com.otc.tinyclassroom.lecture.entity.type.LectureType;
import java.time.LocalDate;

/**
 * 스케줄 리스트 반환 시의 Dto.
 *
 * @param lectureId           강의 pk.
 * @param title               강의 이름.
 * @param lectureType         강의 타입.
 * @param lectureCategoryType 강의 분류.
 * @param scheduleDate        강의 날짜.
 * @param dayOfWeek           강의 요일.
 * @param timeTable           강의 교시.
 * @param deletable           해당 스케줄이 관리자에 의해 추가되었는지. 관리자가 추가하였으면 false.
 */
public record ScheduleListDto(Long lectureId, Long scheduleId, String title, LectureType lectureType,
                              LectureCategoryType lectureCategoryType, LocalDate scheduleDate, int dayOfWeek,
                              int timeTable, Boolean deletable) {

    public static ScheduleListDto of(Long lectureId, Long scheduleId, String title, LectureType lectureType,
        LectureCategoryType lectureCategoryType, LocalDate scheduleDate, int dayOfWeek,
        int timeTable, Boolean deletable) {
        return new ScheduleListDto(lectureId, scheduleId, title, lectureType, lectureCategoryType, scheduleDate, dayOfWeek, timeTable, deletable);
    }
}
