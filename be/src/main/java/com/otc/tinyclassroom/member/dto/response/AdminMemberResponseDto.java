package com.otc.tinyclassroom.member.dto.response;

import com.otc.tinyclassroom.member.dto.ClassRoomDto;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.entity.MemberClassRoom;
import com.otc.tinyclassroom.member.entity.Role;
import java.time.LocalDate;
import java.util.List;

/**
 * Admin 이 관리하기 위한 정보 Dto.
 */
public record AdminMemberResponseDto(String memberId, ClassRoomDto classRooms, String name, String email, LocalDate birthday, int point, Role role) {

    /**
     * 파라미터로 생성하는 팩토리 메서드.
     */
    public static AdminMemberResponseDto of(String memberId, ClassRoomDto classRooms, String name, String email, LocalDate birthday, int point, Role role) {
        return new AdminMemberResponseDto(memberId, classRooms, name, email, birthday, point, role);
    }

    /**
     * 멤버엔티티로 부터 변환한다.
     */
    public static AdminMemberResponseDto from(Member entity) {
        List<MemberClassRoom> classRooms = entity.getMemberClassRooms();
        ClassRoomDto classRoomDto = null;
        if (!classRooms.isEmpty()) {
            MemberClassRoom memberClassRoom = classRooms.get(classRooms.size() - 1);
            classRoomDto = ClassRoomDto.from(memberClassRoom.getClassRoom());
        }
        return new AdminMemberResponseDto(
            entity.getMemberId(),
            classRoomDto,
            entity.getName(),
            entity.getEmail(),
            entity.getBirthday(),
            entity.getPoint(),
            entity.getRole()
        );
    }
}