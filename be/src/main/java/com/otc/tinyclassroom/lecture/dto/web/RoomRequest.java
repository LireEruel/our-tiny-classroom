package com.otc.tinyclassroom.lecture.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 방 생성 요청을 위한 Request Body.
 *
 * @param name       방 이름
 * @param privacy    방 공개여부
 * @param properties 방 특성
 */
public record RoomRequest(@JsonProperty("name") String name, @JsonProperty("privacy") String privacy, @JsonProperty("properties") Properties properties) {

    /**
     * Properties 내부의 변수를 다루기 위한 클래스.
     *
     * @param nbf                  입장가능시간
     * @param exp                  링크만료시간
     * @param maxParticipants      회의 최대 참석 인원 수
     * @param enableEmojiReactions 감정 표현 기능
     * @param enableHandRaising    손들기 기능
     * @param enablePrejoinUi      대기실 Ui 사용 여부
     * @param enableBreakoutRooms  참가자들을 소규모 그룹으로 나누는 기능
     * @param enableKnocking       private 방에 대해 lobby 생성 가능 여부
     * @param enableChat           기본적인 채팅 기능
     * @param enableAdvancedChat   추가 채팅 기능
     * @param enableMeshSfu        multiple SFUs 사용 여부
     * @param lang                 언어지원 (한국어 x)
     * @param geo                  사용할 지역
     */
    public record Properties(@JsonProperty("nbf") long nbf, @JsonProperty("exp") long exp, @JsonProperty("max_participants") int maxParticipants,
                             @JsonProperty("enable_people_ui") boolean enablePeopleUi, @JsonProperty("enable_pip_ui") boolean enablePipUi,
                             @JsonProperty("enable_emoji_reactions") boolean enableEmojiReactions, @JsonProperty("enable_hand_raising") boolean enableHandRaising,
                             @JsonProperty("enable_prejoin_ui") boolean enablePrejoinUi, @JsonProperty("enable_breakout_rooms") boolean enableBreakoutRooms,
                             @JsonProperty("enable_knocking") boolean enableKnocking, @JsonProperty("enable_chat") boolean enableChat,
                             @JsonProperty("enable_advanced_chat") boolean enableAdvancedChat, @JsonProperty("enable_mesh_sfu") boolean enableMeshSfu,
                             @JsonProperty("lang") String lang, @JsonProperty("geo") String geo) {

    }

}
