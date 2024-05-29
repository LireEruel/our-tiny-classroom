package com.otc.tinyclassroom.member.dto.response;

import com.otc.tinyclassroom.member.entity.Member;

/**
 * 게시글 반환시 들고갈 멤버 dto.
 */
public record MemberArticleListResponseDto(String memberId, String name) {

    /**
     *  파라미터로부터 변환한다.
     */
    public static MemberArticleListResponseDto of(String memberId, String name) {
        return new MemberArticleListResponseDto(memberId, name);
    }

    /**
     * 엔티티로 부터 변환한다.
     */
    public static MemberArticleListResponseDto from(Member entity) {
        return new MemberArticleListResponseDto(
            entity.getMemberId(),
            entity.getName()
        );
    }
}
