package com.otc.tinyclassroom.lecture.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.otc.tinyclassroom.lecture.entity.Lecture;
import com.otc.tinyclassroom.lecture.entity.type.LectureApprovalStatusType;
import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import com.otc.tinyclassroom.lecture.entity.type.LectureType;
import com.otc.tinyclassroom.member.dto.response.MemberLectureResponseDto;
import java.time.LocalDate;

/**
 * 강의 DTO.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LectureResponseDto(Long id, MemberLectureResponseDto member, String title, String description, int dayOfWeek, int timeTable,
                                 LectureType lectureType, LectureCategoryType lectureCategoryType, String lectureUrl, LectureApprovalStatusType approved, LocalDate date) {

    public static LectureResponseDto of(Long id, MemberLectureResponseDto member, String title, String description, int dayOfWeek, int timeTable, LectureType lectureType,
        LectureCategoryType lectureCategoryType,
        String lectureUrl, LectureApprovalStatusType approved, LocalDate date) {
        return new LectureResponseDto(id, member, title, description, dayOfWeek, timeTable, lectureType, lectureCategoryType, lectureUrl, approved, date);
    }

    /**
     * Lecture entity -> lecture Dto.
     */
    public static LectureResponseDto from(Lecture entity) {
        return new LectureResponseDto(
            entity.getId(),
            MemberLectureResponseDto.from(entity.getMember()),
            entity.getTitle(),
            entity.getDescription(),
            entity.getDayOfWeek(),
            entity.getTimeTable(),
            entity.getLectureType(),
            entity.getLectureCategoryType(),
            entity.getLectureUrl(),
            entity.getApproved(),
            entity.getDate()
        );
    }
}
