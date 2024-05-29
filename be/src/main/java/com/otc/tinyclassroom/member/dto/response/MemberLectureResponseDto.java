package com.otc.tinyclassroom.member.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.entity.Role;
import java.io.Serializable;

/**
 * Lecture 에 포함된 member(선생, 멘토)에 대한 정보 반환을 위한 Dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MemberLectureResponseDto(String name, Role role) implements Serializable {

    public static MemberLectureResponseDto of(String name, Role role) {
        return new MemberLectureResponseDto(name, role);
    }

    /**
     * 멤버 엔티티를 MemberLectureResponseDto로 변환하는 메소드.
     */
    public static MemberLectureResponseDto from(Member entity) {
        return new MemberLectureResponseDto(
            entity.getName(),
            entity.getRole()
        );
    }


}