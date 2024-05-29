package com.otc.tinyclassroom.global.security.refreshtoken.controller;

import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import com.otc.tinyclassroom.global.security.refreshtoken.dto.request.RefreshRequestDto;
import com.otc.tinyclassroom.global.security.refreshtoken.dto.response.ReIssueResponseDto;
import com.otc.tinyclassroom.global.security.refreshtoken.dto.response.RefreshResponseDto;
import com.otc.tinyclassroom.global.security.refreshtoken.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Refresh Token 과 Redis 관련 로직을 처리하기 위한 Controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/token")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    /**
     * memberId(Long)에 해당하는 Refresh Token 의 TTL(Time to Live)을 조회.
     */
    @Operation(summary = "토큰 TTL 조회", description = "memberId(Long)에 해당하는 Refresh Token 의 TTL(Time to Live)을 조회합니다.", tags = { "리프레시 토큰" })
    @GetMapping("/ttl/{refreshToken}")
    public BaseResponse<?> findTtlByMemberId(@PathVariable("refreshToken") Long refreshToken) {
        Long restTtl = refreshTokenService.getTtlByRefreshToken(String.valueOf(refreshToken));
        if (restTtl > 0) {
            return BaseResponse.success(HttpStatus.OK.value(), "Refresh Token의 남은 유효 시간.", restTtl);
        } else if (restTtl == -1) {
            return BaseResponse.success(HttpStatus.NOT_FOUND.value(), "Refresh Token이 존재합니다.", null);
        } else if (restTtl == -2) {
            return BaseResponse.error(HttpStatus.NOT_FOUND.value(), "Refresh Token이 존재하지 않습니다.");
        }
        return BaseResponse.error(HttpStatus.NOT_FOUND.value(), "Refresh Token이 존재하지 않습니다.");
    }


    /**
     * Access Token 및 Refresh Token 을 사용하여 새로운 Access Token 을 발급.
     */
    @Operation(summary = "Access Token 재발급", description = "Access Token 및 Refresh Token 을 사용하여 새로운 Access Token 을 발급합니다.", tags = { "리프레시 토큰" })
    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<?>> refresh(@RequestBody RefreshRequestDto requestDto) {
        ReIssueResponseDto responseDto = refreshTokenService.reIssue(requestDto.refreshToken());
        // 새로 발급한 Access Token 을 헤더에 넣어 클라이언트에게 전달
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + responseDto.accessToken());
        RefreshResponseDto refreshResponseDto = new RefreshResponseDto(responseDto.refreshToken());
        return ResponseEntity.ok().headers(headers).body(BaseResponse.success(HttpStatus.OK.value(), "Access Token 이 재발급 되었습니다.", refreshResponseDto));
    }
}

