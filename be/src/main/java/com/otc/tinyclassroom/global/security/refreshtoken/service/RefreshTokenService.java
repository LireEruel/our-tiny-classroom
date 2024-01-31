package com.otc.tinyclassroom.global.security.refreshtoken.service;

import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.global.security.refreshtoken.dto.response.ReIssueResponseDto;
import com.otc.tinyclassroom.global.security.refreshtoken.entity.RefreshToken;
import com.otc.tinyclassroom.global.security.refreshtoken.repository.RefreshTokenRepository;
import com.otc.tinyclassroom.member.entity.Role;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Refresh Token 과 관련된 비즈니스 로직을 처리하는 서비스 클래스.
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RedisTemplate<String, String> redisTemplate;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    /**
     * RefreshToken 으로 Redis Repository 조회.
     */
    public String findByRefresh(final String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new NoSuchElementException("Refresh token 에 해당하는 값이 없습니다."));
    }

    /**
     * RefreshToken 으로 TTL 받기.
     */
    public Long getTtlByRefreshToken(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * Refresh Token 을 사용하여 새로운 Access Token 을 발급.
     */
    public ReIssueResponseDto reIssue(String refreshToken) {
        String memberId = this.findByRefresh(refreshToken);
        Role role = memberRepository.findById(Long.valueOf(memberId)).orElseThrow().getRole();

        refreshToken = UUID.randomUUID().toString();
        RefreshToken toRedis = new RefreshToken(refreshToken, memberId);
        refreshTokenRepository.save(toRedis);

        String newAccessToken = jwtProvider.createAccessToken(Long.valueOf(memberId), role);
        return ReIssueResponseDto.of(toRedis.getRefreshToken(), newAccessToken);
    }

    /**
     * refreshToken 삭제.
     */
    public void deleteRefreshToken(Long memberId) {
        redisTemplate.delete(String.valueOf(memberId));
    }

    /**
     * 현재 로그인한 사용자의 Id 가져오기.
     */
    public Long getCurrentUserId() {
        return Long.valueOf(jwtProvider.getCurrentUserId());
    }
}