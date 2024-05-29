package com.otc.tinyclassroom.global.security.refreshtoken.repository;

import com.otc.tinyclassroom.global.security.refreshtoken.entity.RefreshToken;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * Redis 를 사용하여 Refresh Token 을 저장하고 조회하는 Repository.
 */
@Repository
public class RefreshTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.token.refresh-expiration-time}")
    private long refreshTokenTtl;

    public RefreshTokenRepository(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Redis에 리프레쉬토큰을 {토큰: 회원 아이디} 형태로 저장한다.
     *
     * @param refreshToken 리프레쉬 토큰
     */
    public void save(RefreshToken refreshToken) {
        redisTemplate.opsForValue().set(
            refreshToken.getRefreshToken(),
            refreshToken.getMemberId(),
            refreshTokenTtl,
            TimeUnit.MILLISECONDS
        );
    }

    /**
     * 리프레쉬토큰에 해당하는 회원 아이디를 반환한다.
     *
     * @param refreshToken 리프레쉬 토큰
     * @return 리프레쉬 토큰에 해당하는 회원 아이디를 반환한다.
     */
    public Optional<String> findMemberIdByRefreshToken(final String refreshToken) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(refreshToken))
            .map(Object::toString)
            .filter(s -> !s.isEmpty());
    }
}
