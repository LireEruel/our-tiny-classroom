package com.otc.tinyclassroom.lecture.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import com.otc.tinyclassroom.lecture.entity.type.LectureType;
import java.time.LocalDate;

/**
 * 강의 생성 DTO.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LectureCreateUpdateRequestDto(Long memberId, String title, String description, int dayOfWeek, int timeTable, LectureType lectureType, LectureCategoryType lectureCategoryType,
                                            String lectureUrl, LocalDate date) {

}
