package com.samsamgyeesam.studyingvally.global.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF 비활성화 (API 테스트 시 에러 방지)
                .csrf(csrf -> csrf.disable())

                // 2. 🌟 핵심: 어떤 요청(경로)이든 로그인 없이 무조건 통과!
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // 3. 폼 로그인 기능 비활성화 (어차피 다 통과되므로 로그인 창 띄울 필요 없음)
                .formLogin(form -> form.disable());

        return http.build();
    }
}
