package com.otc.tinyclassroom.global.security.jwt;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otc.tinyclassroom.global.common.model.response.BaseResponse;
import com.otc.tinyclassroom.global.security.auth.PrincipalDetails;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * Jwt 권한 관리 필터.
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private MemberRepository memberRepository;
    private JwtProvider jwtProvider;

    public JwtAuthorizationFilter(MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        String servletPath = request.getServletPath();
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        BaseResponse<Void> responseDto = null;

        // 로그인, 리프레시 요청이라면 토큰 검사하지 않음
        if (servletPath.equals("/api/members/login") || servletPath.equals("/api/members/token/refresh") || servletPath.equals("/api/members/join") || servletPath.equals("/api/members/kakao")|| servletPath.startsWith("/swagger-ui") || servletPath.startsWith("/v3")) {
            chain.doFilter(request, response);
        } else if (servletPath.equals("/ws/chat")) {
            // 채팅 소켓 연결이라면 토큰 검사하지 않음.
            chain.doFilter(request, response);
        } else if (header == null || !header.startsWith("Bearer ")) {
            // 토큰값이 없거나 정상적이지 않다면 400 오류
            log.info("CustomAuthorizationFilter : JWT Token 이 존재하지 않습니다.");

            responseDto = new BaseResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "access token 이 존재하지 않음.", null);
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
        } else {
            try {
                String token = jwtProvider.resolveAccessToken(request);
                /*
                 * Expired 되었을 경우
                 * RefreshToken 과 대조해야한다.
                 */
                Long memberId = jwtProvider.getMemberIdByAccessToken(token);
                if (memberId != null) {
                    Member member = memberRepository.findById(memberId).orElse(null);
                    if (member != null) {
                        // Access Token이 재발급되었으므로 사용자 정보를 업데이트
                        PrincipalDetails principalDetails = new PrincipalDetails(member);
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                            principalDetails,
                            null,
                            principalDetails.getAuthorities()
                        );

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        // 사용자 정보를 찾을 수 없는 경우에 대한 처리 (예: 로그아웃)
                        SecurityContextHolder.clearContext();
                    }
                    // 인증 처리 후 정상적으로 다음 Filter 수행
                    chain.doFilter(request, response);
                }
            } catch (TokenExpiredException exception) {
                responseDto = new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), "Access Token이 만료되었습니다.", null);

                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));

            } catch (NullPointerException e) {
                responseDto = new BaseResponse<>(HttpStatus.NOT_FOUND.value(), "해당 객체를 찾을 수 없습니다.", null);

                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
            } catch (Exception e) {
                log.info("AuthorizationFilter에서 에러 : {} ", e.toString());
                responseDto = new BaseResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "잘못된 Access Token입니다.", null);

                response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
            }
        }
    }
}
