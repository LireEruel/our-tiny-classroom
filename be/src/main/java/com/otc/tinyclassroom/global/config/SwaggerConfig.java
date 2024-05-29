package com.otc.tinyclassroom.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "Our Tiny ClassRoom API 명세서",
        description = "Our Tiny ClassRoom API 명세서 입니다.",
        version = "v1.0.0"))
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi customTestOpenAPi() {
        String[] paths = {"/api/**"};

        return GroupedOpenApi
            .builder()
            .group("Our Tiny ClassRoom API")
            .pathsToMatch(paths)
            .build();
    }
}
