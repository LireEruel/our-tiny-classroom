package com.otc.tinyclassroom.lecture.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otc.tinyclassroom.lecture.dto.request.RoomCreateRequestDto;
import com.otc.tinyclassroom.lecture.dto.web.ErrorResponse;
import com.otc.tinyclassroom.lecture.dto.web.RoomRequest;
import com.otc.tinyclassroom.lecture.dto.web.RoomResponse;
import com.otc.tinyclassroom.lecture.exception.LectureErrorCode;
import com.otc.tinyclassroom.lecture.exception.LectureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Daily co 요청을 위한 WebClientService.
 */
@Service
public class WebClientService {

    private final WebClient webClient;

    /**
     * WebClient 생성자.
     */
    public WebClientService(@Value("${daily-co.url}") String baseUrl, @Value("${daily-co.api-key}") String apiKey) {
        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Authorization", "Bearer " + apiKey)
            .build();
    }

    /**
     * 방 생성 API 요청. Mono 비동기 처리 시 사용하는 타입으로 0 ~ 1개의 반환을 한다.
     * <a href="https://projectreactor.io/docs/core/release/reference/index.html#which-operator">참고자료</a>
     *
     * @param requestDto 방 생성에 필요한 파라미터를 담은 request
     * @return 방 URL
     */
    public Mono<String> createClassRoomUrl(RoomCreateRequestDto requestDto) {

        RoomRequest request = new RoomRequest(
            requestDto.roomName(),
            "public",
            new RoomRequest.Properties(
                requestDto.nbf(),
                requestDto.exp(),
                requestDto.maxParticipants(),
                true, true, true, true, false,
                true, true, true, true, true,
                "ko", "ap-northeast-2"
            )
        );

        return webClient.post()
            .uri(uriBuilder -> uriBuilder.path("/rooms").build())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError, this::handleError)
            .bodyToMono(RoomResponse.class)
            .map(RoomResponse::url);
    }

    /**
     * room name 을 통해 생성된 room URL 을 지우는 메소드.
     *
     * @param roomName 지울 방이름
     */
    public Mono<Void> deleteClassRoomUrl(String roomName) {
        return webClient.delete()
            .uri(uriBuilder -> uriBuilder.path("/rooms/" + roomName).build())
            .retrieve()
            .bodyToMono(Void.class);
    }

    private Mono<Throwable> handleError(ClientResponse response) {
        // 에러가 없는 경우 에러 핸들링을 수행하지 않는다.
        if (!response.statusCode().isError()) {
            return Mono.empty();
        }

        // 에러 메세지 정재를 위해 objectMapper 사용
        ObjectMapper objectMapper = new ObjectMapper();
        return response.bodyToMono(String.class)
            .defaultIfEmpty("{\"error\":\"Unknown error\",\"info\":\"No additional information\"}")
            .flatMap(
                errorBody -> {
                    try {
                        ErrorResponse errorResponse = objectMapper.readValue(errorBody, ErrorResponse.class);
                        String errorMesssage = String.format("%s: %s", errorResponse.error(), errorResponse.info());
                        return Mono.error(new LectureException(LectureErrorCode.INVALID_REQUEST_ERROR, errorMesssage));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new LectureException(LectureErrorCode.INVALID_REQUEST_ERROR, "ErrorMessage parsing Error"));
                    }
                }
            );
    }
}
