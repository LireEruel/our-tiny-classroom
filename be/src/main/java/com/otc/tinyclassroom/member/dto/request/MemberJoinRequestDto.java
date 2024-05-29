package com.otc.tinyclassroom.member.dto.request;

import com.otc.tinyclassroom.member.dto.MemberDto;
import java.time.LocalDate;

/**
 * 회원 가입 요청을 위한 DTO.
 */
public record MemberJoinRequestDto(String memberId, String password, String name, String email, LocalDate birthday) {

    public static MemberJoinRequestDto of(String memberId, String password, String name, String email, LocalDate birthday) {
        return new MemberJoinRequestDto(memberId, password, name, email, birthday);
    }

    /**
     * MemberDto 를 MemberJoinRequestDto로 변환하는 메소드.
     */
    public static MemberJoinRequestDto from(MemberDto dto) {
        return new MemberJoinRequestDto(
            dto.memberId(),
            dto.password(),
            dto.name(),
            dto.email(),
            dto.birthday()
        );
    }
}