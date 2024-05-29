package com.otc.tinyclassroom.mypage.repository;

import com.otc.tinyclassroom.mypage.entity.Badge;
import com.otc.tinyclassroom.mypage.entity.MemberBadge;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 뱃지 조회를 위한 리포지토리.
 */
public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {

    Optional<MemberBadge> findByBadgeIdAndMemberId(Long badgeId, Long memberId);

    List<MemberBadge> findByMemberId(Long memberId);
}
