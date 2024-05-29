package com.otc.tinyclassroom.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.otc.tinyclassroom.member.entity.ClassRoom;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 반 DTO.
 * DTO for  {@link com.otc.tinyclassroom.member.entity.ClassRoom}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ClassRoomDto(Long id, int year, int grade, int number, String roomUrl) {

    public static ClassRoomDto of(Long id, int year, int grade, int number, String roomUrl) {
        return new ClassRoomDto(id, year, grade, number, roomUrl);
    }

    /**
     * ClassRoom 엔티티로 변환하는 메소드.
     */
    public static ClassRoom toEntity(ClassRoomDto dto) {
        if (dto == null) {
            return null;
        }
        return ClassRoom.of(dto.year(), dto.grade(), dto.number(), dto.roomUrl());
    }

    /**
     * ClassRoom 엔티티 Set으로 부터 변환하는 메소드.
     */
    public static Set<ClassRoom> toEntity(Set<ClassRoomDto> dtoSet) {
        return dtoSet.stream()
            .map(ClassRoomDto::toEntity)
            .collect(Collectors.toSet());
    }

    /**
     * ClassRoom 엔티티로 부터 변환하는 메소드.
     */
    public static ClassRoomDto from(ClassRoom classRoom) {
        if (classRoom == null) {
            return null;
        }
        return new ClassRoomDto(
            classRoom.getId(),
            classRoom.getYear(),
            classRoom.getGrade(),
            classRoom.getNumber(),
            classRoom.getRoomUrl()
        );
    }

    /**
     * ClassRoom 엔티티 Set으로 부터 변환하는 메소드.
     */
    public static Set<ClassRoomDto> fromEntitySet(Set<ClassRoom> entitySet) {
        return entitySet.stream()
            .map(ClassRoomDto::from)
            .collect(Collectors.toSet());
    }
}
