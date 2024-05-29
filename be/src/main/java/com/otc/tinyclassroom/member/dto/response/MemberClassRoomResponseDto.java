package com.otc.tinyclassroom.member.dto.response;

import com.otc.tinyclassroom.member.dto.ClassRoomDto;
import com.otc.tinyclassroom.member.entity.ClassRoom;
import com.otc.tinyclassroom.member.entity.MemberClassRoom;
import com.otc.tinyclassroom.member.entity.Member;
import java.util.List;

/**
 * ClassRoom 에 소속된 member(학생)에 대한 정보 반환을 위한 Dto.
 */
public record MemberClassRoomResponseDto(String memberId, String name, ClassRoomDto classRoomDto) {

    public static MemberClassRoomResponseDto of(String memberId, String name, ClassRoomDto classRoomDto) {
        return new MemberClassRoomResponseDto(memberId, name, classRoomDto);
    }

    /**
     * 멤버 엔티티를 MemberLectureResponseDto로 변환하는 메소드.
     */
    public static MemberClassRoomResponseDto from(Member entity) {
        List<MemberClassRoom> classRooms = entity.getMemberClassRooms();
        ClassRoomDto dto = null;
        if (!classRooms.isEmpty()) {
            ClassRoom classRoom = classRooms.get(classRooms.size() - 1).getClassRoom();
            dto = ClassRoomDto.from(classRoom);
        }
        return new MemberClassRoomResponseDto(
            entity.getMemberId(),
            entity.getName(),
            dto
        );
    }

    /**
     * 멤버 엔티티를 MemberLectureResponseDto로 변환하는 메소드.
     */
    public static MemberClassRoomResponseDto from(MemberClassRoom entity) {
        Member member = entity.getMember();
        ClassRoom classRoom = entity.getClassRoom();
        return new MemberClassRoomResponseDto(
            member.getMemberId(),
            member.getName(),
            ClassRoomDto.from(classRoom)
        );
    }
}