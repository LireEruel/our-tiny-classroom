package com.otc.tinyclassroom.schedule.dto.request;

import java.time.LocalDate;

/**
 * 스케줄 추가 시 request를 받기 위해 사용하는 Dto.
 *
 * @param lectureId 강의 pk.
 */
public record ScheduleInsertRequestDto(Long lectureId, LocalDate scheduleDate, int timeTable) {

    /**
     * 팩토리 메서드.
     *
     * @param lectureId 강의 pk.
     */
    public static ScheduleInsertRequestDto of(Long lectureId, LocalDate scheduleDate, int timeTable) {
        return new ScheduleInsertRequestDto(lectureId, scheduleDate, timeTable);
    }
}
