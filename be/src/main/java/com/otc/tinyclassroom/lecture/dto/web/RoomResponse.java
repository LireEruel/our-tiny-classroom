package com.otc.tinyclassroom.lecture.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 방생성에 대한 응답을 받기 위한 클래스.
 */
public record RoomResponse(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("api_created") boolean apiCreated, @JsonProperty("privacy") String privacy,
                           @JsonProperty("url") String url, @JsonProperty("created_at") String createdAt, @JsonProperty("config") Config config) {

    /**
     * 요청시 보낸 파라미터.
     */
    public record Config(
        @JsonProperty("nbf") long nbf, @JsonProperty("exp") long exp, @JsonProperty("max_participants") int maxParticipants,
        @JsonProperty("enable_advanced_chat") boolean enableAdvancedChat, @JsonProperty("enable_breakout_rooms") boolean enableBreakoutRooms,
        @JsonProperty("enable_emoji_reactions") boolean enableEmojiReactions, @JsonProperty("enable_chat") boolean enableChat,
        @JsonProperty("enable_hand_raising") boolean enableHandRaising, @JsonProperty("enable_knocking") boolean enableKnocking, @JsonProperty("enable_prejoin_ui") boolean enablePrejoinUi,
        @JsonProperty("enable_people_ui") boolean enablePeopleUi, @JsonProperty("enable_pip_ui") boolean enablePipUi, @JsonProperty("enable_mesh_sfu") boolean enableMeshSfu,
        @JsonProperty("lang") String lang, @JsonProperty("geo") String geo
    ) {

    }
}

