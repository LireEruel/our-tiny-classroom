package com.otc.tinyclassroom.global.config;

import com.otc.tinyclassroom.chat.handler.ChatPreHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 채팅 socket 활성화를 위한 config.
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final ChatPreHandler chatPreHandler;

    /**
     * 메세지를 중간에서 라우팅할 메세지 브로커를 구성.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메세지 발행 prefix 설정. 메세지 브로커는 해당 prefix로 시작하는 메세지만 처리한다.
        registry.setApplicationDestinationPrefixes("/ws/pub");
        // 메세지 구독 요청. 클라이언트가 구독자들에게 메세지를 전달할 url
        registry.enableSimpleBroker("/ws/sub");
    }

    /**
     * 채팅 소켓 연결을 위한 endpoint 설정.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                // websocket 연결 시 요청을 보낼 엔드포인트 설정. ex) localhost:8080/stomp/chat
                .addEndpoint("/ws/chat").setAllowedOriginPatterns("*");
        //         .withSockJS(); // 버전이 낮은 브라우저에세도 적용 가능하게 하는 옵션.
    }

    /**
     * 소켓 메세지 처리를 위한 handler.
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(chatPreHandler);
    }
}
