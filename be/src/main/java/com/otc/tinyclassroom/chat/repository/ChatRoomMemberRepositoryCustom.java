package com.otc.tinyclassroom.chat.repository;

import com.otc.tinyclassroom.chat.dto.ChatRoomMemberDto;
import java.util.List;

/**
 * QueryDsl을 사용하는 ChatRoomMemberRepository Interface.
 */
public interface ChatRoomMemberRepositoryCustom {

    List<ChatRoomMemberDto> findAllRoomMemberByRoomId(String roomId);
}
