package com.otc.tinyclassroom.global.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.global.security.refreshtoken.entity.RefreshToken;
import com.otc.tinyclassroom.global.security.refreshtoken.repository.RefreshTokenRepository;
import com.otc.tinyclassroom.member.client.KakaoClient;
import com.otc.tinyclassroom.member.dto.request.KakaoLoginRequestDto;
import com.otc.tinyclassroom.member.dto.response.KakaoOAuthResponse;
import com.otc.tinyclassroom.member.dto.response.MemberLoginResponseDto;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security + KakaoLogin을 사용하기 위한 Authentication Filter.
 */
@Slf4j
public class KakaoAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    /**
     * AuthenticationFilter 생성자.
     */
    public KakaoAuthenticationFilter(AuthenticationManager authenticationManager, RefreshTokenRepository refreshTokenRepository, JwtProvider jwtProvider, MemberService memberService) {
        super(new AntPathRequestMatcher("/api/members/kakao", "POST"));
        this.authenticationManager = authenticationManager;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtProvider = jwtProvider;
        this.memberService = memberService;
        this.setFilterProcessesUrl("/api/members/kakao");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        log.info("KakaoAuthentication : 진입");

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoLoginRequestDto kakaoLoginRequestDto;

        try {
            kakaoLoginRequestDto = objectMapper.readValue(request.getInputStream(), KakaoLoginRequestDto.class);
            log.info("try loginDto : {}", kakaoLoginRequestDto);
            KakaoClient kakaoClient = new KakaoClient();
            KakaoOAuthResponse kakaoResponse = kakaoClient.getKakaoProfile(kakaoLoginRequestDto.accessToken());
            Member member = memberService.findOrCreateMember(kakaoResponse);

            PrincipalDetails principalDetails = new PrincipalDetails(member);

            // Authentication 객체 생성 및 SecurityContext에 설정
            Authentication authentication = new KakaoAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return authentication;
        } catch (Exception e) {
            log.info("Exception: {}", e.toString());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Set 500 status code
            throw new RuntimeException("서버에서 오류가 발생했습니다.", e);
        }
    }

    // jwt 토큰 생성 및 response 로 반환
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        Member loginMember = principalDetails.getMember();
        String accessToken = jwtProvider.createAccessToken(loginMember.getId(), loginMember.getRole());
        String refreshToken = UUID.randomUUID().toString();
        RefreshToken toRedis = new RefreshToken(refreshToken, loginMember.getId().toString());

        refreshTokenRepository.save(toRedis);
        MemberLoginResponseDto memberLoginResponseDto = new MemberLoginResponseDto(
            loginMember.getId(),
            loginMember.getName(),
            loginMember.getMemberId(),
            loginMember.getRole().getValue(),
            loginMember.getPoint(),
            refreshToken);
        setTokenResponse(response, memberLoginResponseDto);

        jwtProvider.setHeaderAccessToken(response, accessToken);
    }

    private void setTokenResponse(HttpServletResponse response, MemberLoginResponseDto memberLoginResponseDto) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BaseResponse<MemberLoginResponseDto> responseDto = new BaseResponse<>(200, "로그인 성공", memberLoginResponseDto);

        response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
    }
}

