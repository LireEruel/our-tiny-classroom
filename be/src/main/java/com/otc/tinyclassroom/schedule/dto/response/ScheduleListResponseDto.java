package com.otc.tinyclassroom.schedule.dto.response;

import com.otc.tinyclassroom.schedule.dto.ScheduleListDto;
import java.time.LocalDate;
import java.util.List;

/**
 * 스케줄 리스트를 반환하기 위한 Dto.
 *
 * @param monday 한 주의 스케줄을 모은 리스트에서 해당 주의 월요일 날짜.
 */
public record ScheduleListResponseDto(LocalDate monday, List<ScheduleListDto> scheduleList) {

    public static ScheduleListResponseDto of(LocalDate monday, List<ScheduleListDto> scheduleList) {
        return new ScheduleListResponseDto(monday, scheduleList);
    }
}
