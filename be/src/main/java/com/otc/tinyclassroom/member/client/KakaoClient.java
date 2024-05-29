package com.otc.tinyclassroom.member.client;

import com.otc.tinyclassroom.member.dto.response.KakaoOAuthResponse;
import com.otc.tinyclassroom.member.exception.MemberErrorCode;
import com.otc.tinyclassroom.member.exception.MemberException;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 카카오 로그인을 위한 Client.
 */
@Component
public class KakaoClient {

    /**
     * accessToken으로부터 Kakao Profile가져오기.
     */
    public KakaoOAuthResponse getKakaoProfile(String accessToken) {
        Mono<Map<String, Object>> responseMono = sendRequest(accessToken);
        Map<String, Object> attributes = responseMono.block();
        if (attributes != null) {
            return KakaoOAuthResponse.from(attributes);
        } else {
            throw new MemberException(MemberErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private Mono<Map<String, Object>> sendRequest(String accessToken) {

        // WebClient 인스턴스 생성 및 구성
        WebClient kakaoWebClient = WebClient.builder()
            .baseUrl("https://kapi.kakao.com/v2/user/me")
            .defaultHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
            .defaultHeader("Authorization", "Bearer " + accessToken) // accessToken을 여기에 추가
            .build();

        // 요청 보내기
        return kakaoWebClient.post() // 여기서 kakaoWebClient 인스턴스 사용
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<>() {
            });
    }
}
