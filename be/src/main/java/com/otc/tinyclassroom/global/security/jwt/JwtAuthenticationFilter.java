package com.otc.tinyclassroom.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import com.otc.tinyclassroom.global.security.auth.PrincipalDetails;
import com.otc.tinyclassroom.global.security.refreshtoken.entity.RefreshToken;
import com.otc.tinyclassroom.global.security.refreshtoken.repository.RefreshTokenRepository;
import com.otc.tinyclassroom.member.dto.request.MemberLoginRequestDto;
import com.otc.tinyclassroom.member.dto.response.MemberLoginResponseDto;
import com.otc.tinyclassroom.member.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 를 사용하기 위한 Authentication Filter.
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    /**
     *  AuthenticationFilter 생성자.
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, RefreshTokenRepository refreshTokenRepository, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtProvider = jwtProvider;
        this.setFilterProcessesUrl("/api/members/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        log.info("JwtAuthentication : 진입");

        ObjectMapper objectMapper = new ObjectMapper();
        MemberLoginRequestDto loginRequestDto;

        try {
            loginRequestDto = objectMapper.readValue(request.getInputStream(), MemberLoginRequestDto.class);
            log.info("try loginDto : {}", loginRequestDto);

            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.memberId(), loginRequestDto.password());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            log.info("Authentication : {}", principalDetails.getMember().getMemberId());

            return authentication;
        }  catch (AuthenticationException e) {
            log.info("AuthenticationException: {}", e.toString());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // Set 400 status code

            // 클라이언트에게 전달할 오류 메시지 설정
            BaseResponse<String> errorResponse = new BaseResponse<>(HttpStatus.NOT_FOUND.value(), "아이디 혹은 비밀번호가 틀렸습니다.", null);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            try {
                response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            return null;

        } catch (Exception e) {
            log.info("Exception: {}", e.toString());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Set 500 status code
            throw new RuntimeException("서버에서 오류가 발생했습니다.", e);
        }
    }

    // jwt 토큰 생성 및 response 로 반환
    @Override
    protected void successfulAuthentication(
        HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException {

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

