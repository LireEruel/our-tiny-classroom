package com.otc.tinyclassroom.member.repository;

import com.otc.tinyclassroom.member.entity.Member;
import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<Member> findMemberByMemberId(String memberId);

}
