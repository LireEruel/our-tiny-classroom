package com.otc.tinyclassroom.member.repository;

import com.otc.tinyclassroom.member.entity.StudentRoleUpdateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 학생 ROLE Update 요청을 보관하는 Repository.
 */
public interface StudentRoleUpdateRepository extends JpaRepository<StudentRoleUpdateRequest, Long> {

}
