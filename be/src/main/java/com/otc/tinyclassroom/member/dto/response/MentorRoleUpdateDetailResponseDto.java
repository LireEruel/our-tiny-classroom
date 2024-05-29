package com.otc.tinyclassroom.member.dto.response;

import java.time.LocalDate;
import java.util.List;

/**
 * 멘토 등업 게시글 상세보기.
 */
public record MentorRoleUpdateDetailResponseDto(Long articleId, String memberName, LocalDate birthday, String belong, List<String> belongDocumentPaths) {

    public static MentorRoleUpdateDetailResponseDto of(Long articleId, String memberName, LocalDate birthday, String belong, List<String> belongDocumentPaths) {
        return new MentorRoleUpdateDetailResponseDto(articleId, memberName, birthday, belong, belongDocumentPaths);
    }
}

