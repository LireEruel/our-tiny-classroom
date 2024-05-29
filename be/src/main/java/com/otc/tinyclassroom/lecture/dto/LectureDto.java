package com.otc.tinyclassroom.lecture.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.otc.tinyclassroom.lecture.entity.Lecture;
import com.otc.tinyclassroom.lecture.entity.type.LectureApprovalStatusType;
import com.otc.tinyclassroom.lecture.entity.type.LectureCategoryType;
import com.otc.tinyclassroom.lecture.entity.type.LectureType;
import com.otc.tinyclassroom.member.dto.MemberDto;
import java.time.LocalDate;

/**
 * 강의 DTO.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LectureDto(Long id, MemberDto memberDto, String title, String description, int dayOfWeek, int timeTable,
                         LectureType lectureType, LectureCategoryType lectureCategoryType, String lectureUrl, LectureApprovalStatusType approved, LocalDate date) {

    public static LectureDto of(Long id, MemberDto memberDto, String title, String description, int dayOfWeek, int timeTable, LectureType lectureType, LectureCategoryType lectureCategoryType,
        String lectureUrl, LectureApprovalStatusType approved, LocalDate date) {
        return new LectureDto(id, memberDto, title, description, dayOfWeek, timeTable, lectureType, lectureCategoryType, lectureUrl, approved, date);
    }

    /**
     * Lecture entity -> lecture Dto.
     */
    public static LectureDto from(Lecture entity) {
        return new LectureDto(
            entity.getId(),
            MemberDto.from(entity.getMember()),
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

    /**
     * Lecture Dto -> Lecture Entity.
     */
    public static Lecture toEntity(LectureDto lectureDto) {
        return Lecture.of(
            MemberDto.toEntity(lectureDto.memberDto),
            lectureDto.title,
            lectureDto.description,
            lectureDto.dayOfWeek,
            lectureDto.timeTable,
            lectureDto.lectureType,
            lectureDto.lectureCategoryType,
            lectureDto.lectureUrl,
            lectureDto.approved,
            lectureDto.date
        );
    }
}
