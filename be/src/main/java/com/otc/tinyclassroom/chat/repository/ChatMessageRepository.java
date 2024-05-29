package com.otc.tinyclassroom.chat.repository;

import com.otc.tinyclassroom.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 채팅 메세지 repository.
 */
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, ChatMessageRepositoryCustom {
}
