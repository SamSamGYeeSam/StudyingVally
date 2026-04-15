package com.samsamgyeesam.studyingvally.global.Config;

import com.samsamgyeesam.studyingvally.domain.user.service.AuthSuccessHandler;
import com.samsamgyeesam.studyingvally.domain.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Spring Security 설정 클래스이다.
 *
 * 현재 단계의 목표:
 * 1. 서버 실행 시 첫 화면은 /main 으로 보여주기
 * 2. 기본 로그인 페이지(/login) 대신 커스텀 로그인 페이지(/auth/login) 사용
 * 3. user 테이블 기준 로그인 동작 확인
 *
 * 주의:
 * - 현재는 평문 비밀번호 비교를 사용한다.
 * - 실무에서는 NoOpPasswordEncoder를 사용하면 안 된다.
 * - 지금은 부트캠프 팀프로젝트의 최소 로그인 동작 확인용이다.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    // 사용자 인증 시 DB 조회를 담당하는 서비스이다.
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthSuccessHandler authSuccessHandler;

    /*
     * 평문 비밀번호 비교용 PasswordEncoder이다.
     *
     * 현재 DB에 비밀번호가 평문으로 저장되어 있으므로
     * 입력값과 DB값을 그대로 비교하기 위해 사용한다.
     *
     * @return NoOpPasswordEncoder 인스턴스
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /*
     * Security 필터 체인 설정이다.
     *
     * 핵심 포인트:
     * - /main 은 인증 없이 접근 가능해야 한다.
     * - /auth/login 도 인증 없이 접근 가능해야 한다.
     * - 로그인 페이지는 /auth/login 을 사용한다.
     * - 로그인 성공 후 /main 으로 이동한다.
     *
     * @param http HttpSecurity 객체
     * @return SecurityFilterChain
     * @throws Exception 설정 중 예외
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                /*
                 * 사용자 조회 서비스 등록
                 */
                .userDetailsService(customUserDetailsService)

                /**
                 * URL별 인가 정책 설정
                 */
                .authorizeHttpRequests(auth -> auth
                        // 첫 화면, 로그인/회원가입 화면, 정적 리소스는 모두 허용
                        .requestMatchers(
                                "/",
                                "/main",
                                "/auth/login",
                                "/auth/signup1",
                                "/css/**",
                                "/js/**",
                                "/image/**"
                        ).permitAll()

                        // 그 외 요청은 인증 필요
                        .anyRequest().authenticated()
                )

                // 로그인 설정
                .formLogin(login -> login
                        // 커스텀 로그인 페이지 경로
                        .loginPage("/auth/login")

                        // 실제 로그인 처리 URL
                        .loginProcessingUrl("/auth/login")

                        // username 파라미터명
                        .usernameParameter("loginId")

                        // password 파라미터명
                        .passwordParameter("password")

                        // 로그인 성공 시 이동 경로
                        .successHandler(authSuccessHandler)

                        // 로그인 실패 시 다시 로그인 화면으로 이동
                        .failureUrl("/auth/login?error=true")
                )
                /**
                 * 로그아웃 설정
                 *
                 * logoutUrl("/auth/logout")
                 * - 사용자가 이 URL로 요청하면 로그아웃 처리한다.
                 *
                 * logoutSuccessUrl("/main")
                 * - 로그아웃 완료 후 첫 화면으로 이동한다.
                 *
                 * invalidateHttpSession(true)
                 * - 현재 세션을 완전히 무효화한다.
                 *
                 * deleteCookies("JSESSIONID")
                 * - 세션 식별 쿠키를 제거한다.
                 */
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/main")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )

                // 현재 단계에서는 로그아웃 기능을 구현하지 않으므로 별도 설정하지 않는다.

                /*
                 * 학습 단계에서는 CSRF를 잠시 비활성화한다.
                 * 나중에 POST 폼이 늘어나면 CSRF 토큰 적용을 고려해야 한다.
                 */
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
