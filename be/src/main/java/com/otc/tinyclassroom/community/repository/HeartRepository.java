package com.otc.tinyclassroom.community.repository;

import com.otc.tinyclassroom.community.entity.Heart;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 좋아요 Repository.
 */
public interface HeartRepository extends JpaRepository<Heart, Long> {

    // memberId와 articleId로 Heart가 존재하는지 조회한다.
    Optional<Heart> findByMemberIdAndArticleId(Long memberId, Long articleId);

    Page<Heart> findByMemberId(Long memberId, Pageable pageable);
}
