package com.otc.tinyclassroom.schedule.dto.response;

import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import com.otc.tinyclassroom.lecture.entity.type.LectureType;
import com.otc.tinyclassroom.schedule.dto.ScheduleDetailDto;
import java.time.LocalDate;

/**
 * 현재 수강해야 할 강의 정보를 반환하기 위한 Dto.
 */
public record ScheduleUrlResponseDto(Long lectureId, String title, LectureType lectureType,
                                     LectureCategoryType lectureCategoryType, LocalDate scheduleDate, int dayOfWeek,
                                     int timeTable, String lectureUrl) {

    public static ScheduleUrlResponseDto of(Long lectureId, String title, LectureType lectureType,
        LectureCategoryType lectureCategoryType, LocalDate scheduleDate, int dayOfWeek,
        int timeTable, String lectureUrl) {
        return new ScheduleUrlResponseDto(lectureId, title, lectureType, lectureCategoryType, scheduleDate, dayOfWeek, timeTable, lectureUrl);
    }

    /**
     * scheduleDetailDto -> ScheduleUrlResponseDto.
     */
    public static ScheduleUrlResponseDto from(ScheduleDetailDto dto) {
        return new ScheduleUrlResponseDto(
            dto.lectureId(),
            dto.title(),
            dto.lectureType(),
            dto.lectureCategoryType(),
            dto.scheduleDate(),
            dto.dayOfWeek(),
            dto.timeTable(),
            dto.lectureUrl()
        );
    }

    /**
     * scheduleDetailDto, lectureUrl -> ScheduleUrlResponseDto.
     */
    public static ScheduleUrlResponseDto createByRoomUrl(ScheduleDetailDto dto, String roomUrl) {
        return new ScheduleUrlResponseDto(
            dto.lectureId(),
            dto.title(),
            dto.lectureType(),
            dto.lectureCategoryType(),
            dto.scheduleDate(),
            dto.dayOfWeek(),
            dto.timeTable(),
            roomUrl
        );
    }
}
