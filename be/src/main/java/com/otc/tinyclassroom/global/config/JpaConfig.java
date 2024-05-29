package com.otc.tinyclassroom.global.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Auditing을 위한 JPA Config.
 */
@EnableJpaAuditing
@Configuration
public class JpaConfig {

    /**
     * Auditing 기능에서 사용자 정보를 넣어주기 위한 메서드. Security에서 현재 로그인 한 유저의 정보를 가져옴.
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }
            String username = authentication.getName();

            return Optional.of(username);
        };
    }
}
