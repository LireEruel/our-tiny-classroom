package com.otc.tinyclassroom.chat.controller;

import com.otc.tinyclassroom.chat.dto.request.ChatRoomCreateRequestDto;
import com.otc.tinyclassroom.chat.dto.response.ChatMessageResponseDto;
import com.otc.tinyclassroom.chat.dto.response.ChatRoomResponseDto;
import com.otc.tinyclassroom.chat.service.ChatService;
import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 채팅룸 컨트롤러.
 */
@Slf4j
@RestController
@RequestMapping("/api/chatrooms")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;

    /**
     * 채팅룸 생성 메서드.
     */
    @Operation(summary = "채팅룸 생성", description = "채팅룸을 생성합니다.", tags = { "채팅" })
    @PostMapping("")
    public BaseResponse<?> createChatRoom(@RequestBody ChatRoomCreateRequestDto dto) {

        ChatRoomResponseDto chatRoom = chatService.createChatRoom(dto);

        return new BaseResponse<>(HttpStatus.CREATED.value(), "채팅룸이 개설되었습니다.", chatRoom);
    }

    /**
     * 내가 구독하고 있는 채팅룸 리스트를 읽어오는 메서드.
     */
    @Operation(summary = "채팅룸 리스트 조회", description = "내가 속한 모든 채팅룸 리스트를 가져옵니다.", tags = { "채팅" })
    @GetMapping("")
    public BaseResponse<?> findAllChatRoom() {

        List<ChatRoomResponseDto> allSubscribedChatRoom = chatService.findAllSubscribedChatRoom();

        return new BaseResponse<>(HttpStatus.OK.value(), "유저가 속한 모든 채팅룸 리스트를 가져왔습니다.", allSubscribedChatRoom);
    }

    /**
     * 채팅 방에 입장하면 지금까지의 채팅 메세지를 읽어오는 메서드.
     */
    @Operation(summary = "기존 메세지 조회", description = "채팅 방에 입장하면 지금까지의 채팅 메세지를 가져옵니다.", tags = { "채팅" })
    @GetMapping("/{roomId}")
    public BaseResponse<?> findChatMessage(@PathVariable("roomId") String roomId) {

        List<ChatMessageResponseDto> messageList = chatService.findAllChatMessage(roomId);

        return new BaseResponse<>(HttpStatus.OK.value(), "해당 채팅 방의 메세지 리스트가 반환되었습니다.", messageList);
    }

    /**
     * roomId에 해당하는 채팅방에 대한 정보를 읽어오는 메서드.
     */
    @Operation(summary = "채팅방 정보 조회", description = "roomId를 전달하면 해당 채팅방 정보를 가져옵니다.", tags = { "채팅" })
    @GetMapping("/detail/{roomId}")
    public BaseResponse<?> findChatRoomDetail(@PathVariable("roomId") String roomId) {
        ChatRoomResponseDto chatRoomDetail = chatService.findSubscribedChatRoomDetail(roomId);
        return new BaseResponse<>(HttpStatus.OK.value(), "채팅방 정보가 반환되었습니다.", chatRoomDetail);
    }
}
