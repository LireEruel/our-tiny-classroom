package com.otc.tinyclassroom.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.otc.tinyclassroom.global.security.auth.PrincipalDetails;
import com.otc.tinyclassroom.member.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


/**
 * Jwt 토큰 발급을 위한 클래스.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token.access-expiration-time}")
    private long accessExpirationTime;

    private static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 회원 아이디와 등급이 담긴 AccessToken을 생성한다.
     *
     * @param id   회원 아이디(Long)
     * @param role 등급
     * @return AccessToken
     */
    public String createAccessToken(Long id, Role role) {
        return createToken(id, role, accessExpirationTime);
    }

    /**
     * Token을 생성한다.
     *
     * @param memberId   멤버 Id (Long)
     * @param role       멤버 권한
     * @param tokenValid token의 유효시간.
     * @return JWTtoken
     */
    private String createToken(Long memberId, Role role, long tokenValid) {
        return JWT.create()
            .withExpiresAt(new Date(System.currentTimeMillis() + tokenValid))
            .withClaim("memberId", memberId)
            .withClaim("role", role.name())
            .sign(Algorithm.HMAC512(secret));
    }

    /**
     * AccessToken으로부터 회원 아이디를 가져온다.
     *
     * @param token AccessToken
     */
    public Long getMemberIdByAccessToken(String token) {
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(token)
            .getClaim("memberId").asLong();
    }

    /**
     * AccessToken으로부터 권한을 가져온다.
     *
     * @param token AccessToken
     * @return 권한
     */
    public String getRoleByAccessToken(String token) {
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(token)
            .getClaim("role").asString();
    }

    /**
     * 현재 사용자의 아이디(Long)을 가져온다.
     *
     * @return 회원 아이디
     */
    public Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof PrincipalDetails) {
                return ((PrincipalDetails) principal).getMember().getId();
            } else if (principal instanceof UserDetails) {
                return Long.valueOf(((UserDetails) principal).getUsername());
            } else {
                // 만약 principal이 UserDetails가 아닌 다른 타입이면, 해당 타입에 맞게 처리
                return Long.valueOf(principal.toString());
            }
        }
        return null; // 인증된 사용자가 없는 경우
    }

    /**
     * AccessToken을 Header에 넣어 요청을 보낸 경우 Token의 Prefix를 제거한다.
     *
     * @param request 요청
     * @return Bearer가 제거된 AccessToken
     */
    public String resolveAccessToken(HttpServletRequest request) {
        if (request.getHeader("authorization") != null) {
            return request.getHeader("authorization").replace(TOKEN_PREFIX, "");
        }
        return null;
    }

    /**
     * accessToken을 응답헤더에 넣는다.
     *
     * @param response    응답
     * @param accessToken accessToken
     */
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("authorization", "Bearer " + accessToken);
    }
}
