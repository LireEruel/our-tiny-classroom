package com.otc.tinyclassroom.chat.service;

import com.otc.tinyclassroom.chat.dto.ChatRoomDto;
import com.otc.tinyclassroom.chat.dto.request.ChatMessageCreateRequestDto;
import com.otc.tinyclassroom.chat.dto.request.ChatRoomCreateRequestDto;
import com.otc.tinyclassroom.chat.dto.response.ChatMessageResponseDto;
import com.otc.tinyclassroom.chat.dto.response.ChatRoomResponseDto;
import com.otc.tinyclassroom.chat.entity.ChatMessage;
import com.otc.tinyclassroom.chat.entity.ChatRoom;
import com.otc.tinyclassroom.chat.entity.ChatRoomMember;
import com.otc.tinyclassroom.chat.entity.type.ChatMessageType;
import com.otc.tinyclassroom.chat.exception.ChatErrorCode;
import com.otc.tinyclassroom.chat.exception.ChatException;
import com.otc.tinyclassroom.chat.repository.ChatMessageRepository;
import com.otc.tinyclassroom.chat.repository.ChatRoomMemberRepository;
import com.otc.tinyclassroom.chat.repository.ChatRoomRepository;
import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 채팅룸 서비스.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    /**
     * 채팅방을 만들고 roomId 값을 반환하는 메서드.
     */
    public ChatRoomResponseDto createChatRoom(ChatRoomCreateRequestDto dto) {
        Long currentUserId = jwtProvider.getCurrentMemberId();
        Member fromMember = memberRepository.findById(currentUserId)
                .orElseThrow(() -> new ChatException(ChatErrorCode.NOT_FOUND_MEMBER));

        Member toMember = memberRepository.findByMemberId(dto.targetMemberId())
                .orElseThrow(() -> new ChatException(ChatErrorCode.NOT_FOUND_MEMBER));

        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.of());

        chatRoomMemberRepository.save(ChatRoomMember.of(fromMember, chatRoom));
        chatRoomMemberRepository.save(ChatRoomMember.of(toMember, chatRoom));

        simpMessageSendingOperations.convertAndSend(
                "/ws/sub/room/" + toMember.getMemberId(),
                ChatMessageResponseDto.of(
                        null,
                        chatRoom.getId(),
                        fromMember.getMemberId(),
                        fromMember.getName(),
                        "채팅방에 초대되었습니다.",
                        LocalDateTime.now(),
                        ChatMessageType.SUBSCRIBE
                )
        );

        return ChatRoomResponseDto.of(
                chatRoom.getId(),
                chatRoomMemberRepository.findAllRoomMemberByRoomId(chatRoom.getId()),
                null,
                null,
                new ArrayList<ChatMessageResponseDto>()
        );
    }

    /**
     * 유저가 속해 있는 모든 채팅방을 가져오는 메서드.
     */
    public List<ChatRoomResponseDto> findAllSubscribedChatRoom() {

        Long currentUserId = jwtProvider.getCurrentMemberId();
        Member currentMember = memberRepository.findById(currentUserId)
                .orElseThrow(() -> new ChatException(ChatErrorCode.NOT_FOUND_MEMBER));

        List<ChatRoomDto> chatRoomList = chatRoomRepository.findAllChatRoomByMemberId(currentMember.getId());
        List<ChatRoomResponseDto> chatRoomResponseList = new ArrayList<>();
        for (ChatRoomDto dto : chatRoomList) {
            chatRoomResponseList.add(ChatRoomResponseDto.of(
                    dto.roomId(),
                    chatRoomMemberRepository.findAllRoomMemberByRoomId(dto.roomId()),
                    dto.lastChatId(),
                    dto.lastMessage(),
                    new ArrayList<ChatMessageResponseDto>()
            ));
        }

        return chatRoomResponseList;
    }

    /**
     * 채팅룸 정보를 가져오는 메서드.
     */
    @Transactional(readOnly = true)
    public ChatRoomResponseDto findSubscribedChatRoomDetail(String roomId) {

        ChatRoomDto chatRoomDetail = chatRoomRepository.findChatRoomDetail(roomId);
        return ChatRoomResponseDto.of(
                chatRoomDetail.roomId(),
                chatRoomMemberRepository.findAllRoomMemberByRoomId(chatRoomDetail.roomId()),
                chatRoomDetail.lastChatId(),
                chatRoomDetail.lastMessage(),
                new ArrayList<ChatMessageResponseDto>()
        );
    }

    /**
     * 채팅 메세지를 저장하는 메서드.
     */
    public ChatMessageResponseDto saveChatMessage(ChatMessageCreateRequestDto dto) {

        ChatRoom chatRoom = chatRoomRepository.findById(dto.roomId())
                .orElseThrow(() -> new ChatException(ChatErrorCode.NOT_FOUND_CHATROOM, "존재하지 않는 채팅룸 번호입니다."));

        Member member = memberRepository.findByMemberId(dto.senderId())
                .orElseThrow(() -> new ChatException(ChatErrorCode.NOT_FOUND_MEMBER, "존재하지 않는 유저 이름입니다."));

        ChatMessage chatMessage = ChatMessage.of(chatRoom, member, dto.message(), ChatMessageType.MESSAGE);
        ChatMessage saveMessage = chatMessageRepository.save(chatMessage);

        // 해당 채팅방의 마지막 채팅 변경.
        chatRoom.updateLastChatMessage(saveMessage);
        chatRoomRepository.save(chatRoom);

        return ChatMessageResponseDto.of(
                saveMessage.getId(),
                saveMessage.getChatRoom().getId(),
                saveMessage.getMember().getMemberId(),
                saveMessage.getMember().getName(),
                saveMessage.getMessage(),
                saveMessage.getCreatedAt(),
                saveMessage.getChatMessageType()
        );
    }

    /**
     * 채팅 룸 번호에 맞는 채팅 리스트를 가져오는 메서드.
     */
    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> findAllChatMessage(String roomId) {
        return chatMessageRepository.findAllChatByRoomId(roomId);
    }

}
