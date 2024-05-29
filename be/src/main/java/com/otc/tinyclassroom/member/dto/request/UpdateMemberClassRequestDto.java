package com.otc.tinyclassroom.member.dto.request;

/**
 * 멤버 하나 반 편성 request 를 위한 Dto.
 */
public record UpdateMemberClassRequestDto(Long memberId, Long classRoomId) {

}
