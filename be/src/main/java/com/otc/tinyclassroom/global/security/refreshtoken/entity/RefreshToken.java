package com.otc.tinyclassroom.global.security.refreshtoken.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Redis에서 관리할 RefreshToken Entity.
 */
@Getter
@RedisHash(value = "refreshToken")
public class RefreshToken {

    @Id
    private String refreshToken;
    private final String memberId;

    public RefreshToken(String refreshToken, String memberId) {
        this.refreshToken = refreshToken;
        this.memberId = memberId;
    }
}