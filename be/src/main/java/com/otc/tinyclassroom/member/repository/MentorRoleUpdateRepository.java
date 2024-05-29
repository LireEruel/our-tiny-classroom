package com.otc.tinyclassroom.member.repository;

import com.otc.tinyclassroom.member.entity.MentorRoleUpdateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 멘토 ROLE Update 요청을 보관하는 Repository.
 */
public interface MentorRoleUpdateRepository extends JpaRepository<MentorRoleUpdateRequest, Long> {

}
