package com.otc.tinyclassroom.chat.repository;

import com.otc.tinyclassroom.chat.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ChatRoom과 Member 매핑 테이블 repository.
 */
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long>, ChatRoomMemberRepositoryCustom {
}
