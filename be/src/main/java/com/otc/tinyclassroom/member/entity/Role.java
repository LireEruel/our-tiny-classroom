package com.otc.tinyclassroom.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 사용자 ROLE 을 정의한 Enum.
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_USER("USER"), ROLE_STUDENT("STUDENT"), ROLE_TEACHER("TEACHER"), ROLE_MENTOR("MENTOR"), ROLE_ADMIN("ADMIN"), ROLE_WAITING("WAITING");

    private final String value;
}
