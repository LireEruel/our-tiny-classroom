package com.otc.tinyclassroom.chat.repository;

import com.otc.tinyclassroom.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 채팅룸 repository.
 */
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String>, ChatRoomRepositoryCustom {
}
