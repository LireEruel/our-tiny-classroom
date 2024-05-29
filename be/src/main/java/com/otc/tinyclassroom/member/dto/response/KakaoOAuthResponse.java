package com.otc.tinyclassroom.member.dto.response;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * accessToken으로부터 받아 오는 응답.
 */
@SuppressWarnings({"unchecked", "checkstyle:AbbreviationAsWordInName"})
public record KakaoOAuthResponse(
    Long id,
    LocalDateTime connectedAt,
    Map<String, Object> properties,
    KakaoAccount kakaoAccount
) {

    /**
     * 카카오 계정.
     *
     * @param profileNicknameNeedsAgreement 닉네임 동의
     * @param profile                       카카오 프로필
     * @param hasEmail                      이메일 가지고 있는지 여부
     * @param emailNeedsAgreement           이메일 동의 여부
     * @param isEmailValid                  이메일 유효 여부
     * @param isEmailVerified               이메일 인증 여부
     * @param email                         이메일
     */
    public record KakaoAccount(
        Boolean profileNicknameNeedsAgreement,
        Profile profile,
        Boolean hasEmail,
        Boolean emailNeedsAgreement,
        Boolean isEmailValid,
        Boolean isEmailVerified,
        String email
    ) {

        /**
         * 카카오 프로필.
         *
         * @param nickname 닉네임
         */
        public record Profile(String nickname) {

            public static Profile from(Map<String, Object> attributes) {
                return new Profile(String.valueOf(attributes.get("nickname")));
            }
        }

        /**
         * 응답에 대해 프로필로 변환한다.
         */
        public static KakaoAccount from(Map<String, Object> attributes) {
            return new KakaoAccount(
                Boolean.valueOf(String.valueOf(attributes.get("profile_nickname_needs_agreement"))),
                Profile.from((Map<String, Object>) attributes.get("profile")),
                Boolean.valueOf(String.valueOf(attributes.get("has_email"))),
                Boolean.valueOf(String.valueOf(attributes.get("email_needs_agreement"))),
                Boolean.valueOf(String.valueOf(attributes.get("is_email_valid"))),
                Boolean.valueOf(String.valueOf(attributes.get("is_email_verified"))),
                String.valueOf(attributes.get("email"))
            );
        }

        public String nickname() {
            return this.profile().nickname();
        }
    }

    /**
     * 응답을 객체로 변환한다.
     */
    public static KakaoOAuthResponse from(Map<String, Object> attributes) {
        return new KakaoOAuthResponse(
            Long.valueOf(String.valueOf(attributes.get("id"))),
            LocalDateTime.parse(
                String.valueOf(attributes.get("connected_at")),
                DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault())
            ),
            (Map<String, Object>) attributes.get("properties"),
            KakaoAccount.from((Map<String, Object>) attributes.get("kakao_account"))
        );
    }

    public String email() {
        return this.kakaoAccount().email();
    }

    public String nickname() {
        return this.kakaoAccount().nickname();
    }
}