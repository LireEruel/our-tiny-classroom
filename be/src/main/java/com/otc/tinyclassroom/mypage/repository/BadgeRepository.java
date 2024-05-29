package com.otc.tinyclassroom.mypage.repository;

import com.otc.tinyclassroom.mypage.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 뱃지를 저장하는 리포지토리.
 */
public interface BadgeRepository extends JpaRepository<Badge, Long> {

}
