package com.otc.tinyclassroom.chat.repository;

import com.otc.tinyclassroom.chat.dto.ChatRoomDto;
import java.util.List;

/**
 * Querydsl을 사용하는 ChatRoomRepository Interface.
 */
public interface ChatRoomRepositoryCustom {

    List<ChatRoomDto> findAllChatRoomByMemberId(Long userId);

    ChatRoomDto findChatRoomDetail(String roomId);

}
