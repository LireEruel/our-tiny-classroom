package com.otc.tinyclassroom.chat.handler;

import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

/**
 * 소켓 연결 요청을 가로채기 위한 Interceptor.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ChatPreHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        log.info("ChatPreHandler 진입");
        log.info("Command : {}", headerAccessor.getCommand());

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            String accessToken = String.valueOf(headerAccessor.getNativeHeader("Authorization"));

            // TODO : jwt 토큰을 이용한 유저 인증.

            if (accessToken == null || accessToken.equals("null")) {
                log.info("Socket : No Authorization");
                throw new MessageDeliveryException("Socket : Access Token 이 없습니다.");
            }
            log.info("Authorization : {}", accessToken.substring(7));
        }

        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
            log.info("SUBSCRIBE >> sessionId : {}", headerAccessor.getSessionId());
        }

        return message;
    }
}
