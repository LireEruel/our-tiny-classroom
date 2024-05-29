package com.otc.tinyclassroom.chat.controller;

import com.otc.tinyclassroom.chat.dto.request.ChatMessageCreateRequestDto;
import com.otc.tinyclassroom.chat.dto.response.ChatMessageResponseDto;
import com.otc.tinyclassroom.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

/**
 * chat 기능을 위한 controller.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    // 특정 브로커로 메세지 전달.
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ChatService chatService;

    /**
     * 클라이언트가 /pub/message 으로 메세지를 발행.
     */
    @Operation(summary = "메세지 전달", description = "클라이언트가 메세지를 발행합니다.", tags = { "메세지 발행" })
    @MessageMapping("/message")
    public void message(ChatMessageCreateRequestDto dto) {
        ChatMessageResponseDto responseDto = chatService.saveChatMessage(dto);
        simpMessageSendingOperations.convertAndSend("/ws/sub/room/" + dto.roomId(), responseDto);
        log.info("Room : {}, message : {}", responseDto.roomId(), responseDto.message());
    }
}
