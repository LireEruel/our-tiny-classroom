package com.otc.tinyclassroom.global.config;

import com.otc.tinyclassroom.global.security.auth.KakaoAuthenticationFilter;
import com.otc.tinyclassroom.global.security.jwt.JwtAuthenticationFilter;
import com.otc.tinyclassroom.global.security.jwt.JwtAuthorizationFilter;
import com.otc.tinyclassroom.global.security.jwt.JwtProvider;
import com.otc.tinyclassroom.global.security.refreshtoken.repository.RefreshTokenRepository;
import com.otc.tinyclassroom.member.entity.Role;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import com.otc.tinyclassroom.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security Configuration 추가.
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final RefreshTokenRepository refreshTokenRepository;

    private final CorsConfig corsConfig;

    private final JwtProvider jwtProvider;

    private final MemberRepository memberRepository;

    private final MemberService memberService;


    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * filter chain 처리.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // csrf 토큰 방어 해제.
            .csrf(AbstractHttpConfigurer::disable)
            // cors 허용
            .addFilter(corsConfig.corsFilter())
            // 서버를 Stateless 하게 유지.
            .sessionManagement((sessionManagement) -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .formLogin(AbstractHttpConfigurer::disable)
            // httpBasic 인증 방식 해제.
            .httpBasic(AbstractHttpConfigurer::disable)


            // 로그인 이전에 access token을 처리할 jwt 인가 필터 추가.
            .addFilterBefore(new JwtAuthorizationFilter(memberRepository, jwtProvider), UsernamePasswordAuthenticationFilter.class)
            // jwt 인증 필터 추가.
            .addFilterBefore(new JwtAuthenticationFilter(authenticationManager(), refreshTokenRepository, jwtProvider), UsernamePasswordAuthenticationFilter.class)
            // kakao 인증 필터 추가.
            .addFilterBefore(new KakaoAuthenticationFilter(authenticationManager(), refreshTokenRepository, jwtProvider, memberService), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests((authorizerRequests) -> authorizerRequests
                .requestMatchers(("/api/members/join")).permitAll()
                .requestMatchers("/api/members/login").permitAll()
                .requestMatchers("/api/members/currentMember").hasAuthority(Role.ROLE_ADMIN.name())
                .requestMatchers("/api/attendances/**").permitAll()
                .requestMatchers("/api/community/**").permitAll()
                .requestMatchers("/api/classrooms/**").permitAll()
                .requestMatchers("/api/lectures/**").permitAll()
                //.requestMatchers("/student/**").hasAuthority("ROLE_STUDENT")
                //.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll()
            )
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            );
        return http.build();
    }
}
