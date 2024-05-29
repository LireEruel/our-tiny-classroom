package com.otc.tinyclassroom.global.security.auth;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 카카오 인증을 위한 Token.
 */
public class KakaoAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal; // 사용자의 주요 정보를 담습니다.
    private Object credentials; // 인증에 사용된 자격증명, 카카오 인증에서는 사용되지 않을 수 있습니다.

    /**
     * 파라미터 생성자.
     */
    public KakaoAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // 인증 완료로 설정
    }

    @Override
    public Object getCredentials() {
        return this.credentials; // 자격증명 정보 반환, 카카오 인증에서는 일반적으로 사용되지 않습니다.
    }

    @Override
    public Object getPrincipal() {
        return this.principal; // 사용자 정보 반환
    }

    // 인증 상태를 변경하는 setAuthenticated 메소드를 오버라이드하여,
    // 외부에서 호출할 수 없도록 예외를 발생시키는 것이 좋습니다.
    // 이는 보안상 인증 과정 외에 인증 상태를 변경할 수 없도록 제한하기 위함입니다.
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null; // 자격증명 정보를 제거하여 메모리에서 안전하게 처리합니다.
    }
}