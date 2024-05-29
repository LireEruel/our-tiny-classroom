package com.otc.tinyclassroom.member.repository;

import com.otc.tinyclassroom.member.entity.MemberClassRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * member ClassRoom 맵핑 Repository.
 */
public interface MemberClassRoomRepository extends JpaRepository<MemberClassRoom, Long>, MemberClassRoomRepositoryCustom {

    List<MemberClassRoom> findByMemberId(Long memberId);

    List<MemberClassRoom> findByClassRoom_Id(Long classRoomId);

    List<MemberClassRoom> findByMember_Id(Long memberId);

    List<MemberClassRoom> findByMember_MemberId(String memberMemberId);

    List<MemberClassRoom> findByMember_IdAndClassRoom_Id(Long memberId, Long classRoomId);
}