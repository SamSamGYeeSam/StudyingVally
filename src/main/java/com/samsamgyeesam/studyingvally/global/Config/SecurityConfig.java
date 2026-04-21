package com.samsamgyeesam.studyingvally.global.Config;

import com.samsamgyeesam.studyingvally.domain.user.service.AuthFailHandler;
import com.samsamgyeesam.studyingvally.domain.user.service.AuthSuccessHandler;
import com.samsamgyeesam.studyingvally.domain.user.service.AuthUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // 로그인 시 사용자 정보를 조회하는 서비스
    private final AuthUserDetailsService authUserDetailsService;

    // 로그인 성공 시 역할별 메인 페이지로 보내는 핸들러
    private final AuthSuccessHandler authSuccessHandler;

    // 로그인 실패 시 에러 메시지 처리 핸들러
    private final AuthFailHandler authFailHandler;

    // css, js, image 같은 정적 리소스는 시큐리티 검사 제외
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 개발 단계 기준 csrf 비활성화
                .csrf(csrf -> csrf.disable())

                // URL 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 비로그인도 접근 가능한 공개 페이지
                        .requestMatchers(
                                "/",
                                "/basic",
                                "/main",
                                "/auth/**",
                                "/image/**"
                        ).permitAll()

                        // 학생 전용 경로
                        .requestMatchers("/student/**").hasRole("STUDENT")

                        // 강사 전용 경로
                        // 강사 관련 URL이 /teacher/**로 통일되어 있지 않아서
                        // 루트 경로도 함께 묶어준다.
                        .requestMatchers(
                                "/teacher/**",
                                "/showinformation",
                                "/updateinformation",
                                "/deleteaccount",
                                "/showinformation/check-password"
                        ).hasRole("TEACHER")

                        // 관리자 전용 경로
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 그 외 요청은 로그인 필요
                        .anyRequest().authenticated()
                )

                // 로그인 설정
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .successHandler(authSuccessHandler)
                        .failureHandler(authFailHandler)
                        .permitAll()
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/main")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )

                // 잘못된 페이지 경로로 갔을 때 오류
                .exceptionHandling(exception -> exception
                .accessDeniedPage("/error-page")
        )

                // UserDetailsService 명시 연결
                .userDetailsService(authUserDetailsService);

        return http.build();
    }
}