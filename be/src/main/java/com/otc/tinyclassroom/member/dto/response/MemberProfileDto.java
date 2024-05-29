package com.otc.tinyclassroom.member.dto.response;

import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.entity.Role;
import java.time.LocalDate;

/**
 * Member Profile Dto.
 */
public record MemberProfileDto(String memberId, String name, LocalDate birthday, Role role, String profileUrl) {

    /**
     * entity로부터 변환한다.
     */
    public static MemberProfileDto from(Member entity) {
        return new MemberProfileDto(
            entity.getMemberId(),
            entity.getName(),
            entity.getBirthday(),
            entity.getRole(),
            entity.getProfileUrl()
        );
    }
}