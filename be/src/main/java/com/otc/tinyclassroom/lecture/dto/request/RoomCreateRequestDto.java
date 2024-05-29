package com.otc.tinyclassroom.lecture.dto.request;

/**
 * 방생성 요청시 조건을 받기 위한 Dto.
 *
 * @param roomName        생성될 방 이름 URL 에 포함되기 때문에 Unique 해야함
 * @param nbf             입장가능시간 (Unix TimeStamp)
 * @param exp             방 만료시간 (Unix TimeStamp)
 * @param maxParticipants 회의에 참여 가능한 최대 인원 (max: 50)
 */
public record RoomCreateRequestDto(String roomName, long nbf, long exp, int maxParticipants) {

    public static RoomCreateRequestDto of(String roomName, long nbf, long exp, int maxParticipants) {
        return new RoomCreateRequestDto(roomName, nbf, exp, maxParticipants);
    }
}