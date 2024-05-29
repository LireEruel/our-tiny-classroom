package com.otc.tinyclassroom.global.security.auth;

import com.otc.tinyclassroom.member.entity.Member;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User 정보(권한, 이름, 비밀번호 등)를 관리하는 구현체.
 */
@Slf4j
@Getter
public class PrincipalDetails implements UserDetails {

    private final Member member;

    public PrincipalDetails(Member member) {
        log.info("member = {}", member);
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority(member.getRole().name()));
        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getMemberId();
    }

    /**
     * 계정이 만료되지 않았는지 검증.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정이 잠기지 않았는지 검증.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 비밀번호가 만료되지 않았는지 검증.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정이 활상화 상태인지 검증.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
