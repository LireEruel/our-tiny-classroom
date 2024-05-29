package com.otc.tinyclassroom.global.security.refreshtoken.service;

import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.global.security.refreshtoken.dto.response.ReIssueResponseDto;
import com.otc.tinyclassroom.global.security.refreshtoken.entity.RefreshToken;
import com.otc.tinyclassroom.global.security.refreshtoken.exception.RefreshTokenErrorCode;
import com.otc.tinyclassroom.global.security.refreshtoken.exception.RefreshTokenException;
import com.otc.tinyclassroom.global.security.refreshtoken.repository.RefreshTokenRepository;
import com.otc.tinyclassroom.member.entity.Role;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Refresh Token 과 관련된 비즈니스 로직을 처리하는 Service.
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    /**
     * RefreshToken으로부터 멤버 아이디를 반환한다.
     *
     * @param refreshToken 리프레쉬 토큰
     * @return 회원 Id
     */
    public String findMemberIdByRefreshToken(final String refreshToken) {
        return refreshTokenRepository.findMemberIdByRefreshToken(refreshToken)
            .orElseThrow(() -> new NoSuchElementException("Refresh token이 없습니다."));
    }

    /**
     * refreshToken으로부터 TTL을 반환. 양수: 남아있는 TTL -1: 영구보관 -2: 키가 존재하지 않음
     *
     * @param refreshToken refreshToken
     * @return TTL 상태
     */
    public Long getTtlByRefreshToken(String refreshToken) {
        return redisTemplate.getExpire(refreshToken, TimeUnit.SECONDS);
    }

    /**
     * RefreshToken을 통해 모든 Token을 재발급한다.
     *
     * @param refreshToken 리프레쉬 토큰
     * @return 새로 발급된 Refresh Token과  AccessToken을 담은 DTO
     */
    public ReIssueResponseDto reIssue(String refreshToken) {
        String memberId = this.findMemberIdByRefreshToken(refreshToken);
        redisTemplate.delete(String.valueOf(refreshToken));
        Role role = memberRepository.findById(Long.valueOf(memberId)).orElseThrow().getRole();

        refreshToken = UUID.randomUUID().toString();
        RefreshToken toRedis = new RefreshToken(refreshToken, memberId);
        refreshTokenRepository.save(toRedis);

        String newAccessToken = jwtProvider.createAccessToken(Long.valueOf(memberId), role);
        return ReIssueResponseDto.of(toRedis.getRefreshToken(), newAccessToken);
    }

    /**
     * 현재 로그인한 사용자의 Refresh Token 을 찾음.
     */
    public String getRefreshTokenByUserId(Long memberId) {
        String stringMemberId = memberId.toString();

        Optional<String> refreshToken = Optional.ofNullable(redisTemplate.keys("*"))
            .stream().flatMap(Collection::stream)
            .map(key -> redisTemplate.opsForValue().get(key))
            .filter(stringMemberId::equals)
            .findFirst();

        return refreshToken.orElseThrow(() -> new RefreshTokenException(RefreshTokenErrorCode.NOT_EXIST_TOKEN));
    }

    /**
     * refreshToken 삭제.
     */
    public void deleteRefreshToken(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }

    /**
     * 현재 로그인한 사용자 아이디 가져오기.
     *
     * @return 현재 로그인한 유저 아이디 (Long)
     */
    public Long getCurrentMemberId() {
        return jwtProvider.getCurrentMemberId();
    }
}
