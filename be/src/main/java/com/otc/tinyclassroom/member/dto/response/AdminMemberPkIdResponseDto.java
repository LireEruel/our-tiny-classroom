package com.otc.tinyclassroom.member.dto.response;

import com.otc.tinyclassroom.member.dto.ClassRoomDto;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.entity.MemberClassRoom;
import com.otc.tinyclassroom.member.entity.Role;
import java.time.LocalDate;
import java.util.List;

public record AdminMemberPkIdResponseDto(Long userId, String memberId, ClassRoomDto classRooms, String name, String email, LocalDate birthday, int point, Role role) {

    public static AdminMemberPkIdResponseDto of(Long userId, String memberId, ClassRoomDto classRooms, String name, String email, LocalDate birthday, int point, Role role) {
        return new AdminMemberPkIdResponseDto(userId, memberId, classRooms, name, email, birthday, point, role);
    }


    public static AdminMemberPkIdResponseDto from(Member entity) {
        List<MemberClassRoom> classRooms = entity.getMemberClassRooms();
        ClassRoomDto classRoomDto = null;
        if (!classRooms.isEmpty()) {
            MemberClassRoom memberClassRoom = classRooms.get(classRooms.size() - 1);
            classRoomDto = ClassRoomDto.from(memberClassRoom.getClassRoom());
        }
        return new AdminMemberPkIdResponseDto(
            entity.getId(),
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
