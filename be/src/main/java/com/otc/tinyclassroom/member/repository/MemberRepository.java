package com.otc.tinyclassroom.member.repository;

import com.otc.tinyclassroom.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 회원 Repository.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    // 회원 아이디 검색
    Optional<Member> findByMemberId(String memberId);
}