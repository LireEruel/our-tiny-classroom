package com.otc.tinyclassroom.member.dto.request;

import java.util.List;

/**
 * 다수 멤버 반 편성 request 를 위한 Dto.
 */
public record AssignmentMembersRequestDto(List<Long> memberIds, Long classRoomId) {

}
