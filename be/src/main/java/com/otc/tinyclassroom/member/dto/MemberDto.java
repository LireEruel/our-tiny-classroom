package com.otc.tinyclassroom.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.otc.tinyclassroom.member.entity.Member;
import java.time.LocalDate;

/**
 * 기본 멤버 DTO. DTO for {@link com.otc.tinyclassroom.member.entity.Member}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MemberDto(String memberId, String password, String name, String email, LocalDate birthday, int point, String profileUrl) {

    public static MemberDto of(String memberId, String password, String name, String email, LocalDate birthday, int point, String profileUrl) {
        return new MemberDto(memberId, password, name, email, birthday, point, profileUrl);
    }

    /**
     * Member 엔티티를 MemberDto로 변환하는 메소드.
     */
    public static MemberDto from(Member entity) {
        return new MemberDto(
            entity.getMemberId(),
            entity.getPassword(),
            entity.getName(),
            entity.getEmail(),
            entity.getBirthday(),
            entity.getPoint(),
            entity.getProfileUrl()
        );
    }


    /**
     * MemberDto를 Member로 변환하는 메소드.
     */
    public static Member toEntity(MemberDto dto) {
        return Member.of(
            dto.memberId(),
            dto.password(),
            dto.name(),
            dto.email(),
            dto.birthday(),
            dto.point(),
            dto.profileUrl()
        );
    }

}
