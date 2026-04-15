//package com.samsamgyeesam.studyingvally.global.Config;
//
//import com.samsamgyeesam.studyingvally.domain.user.service.AuthSuccessHandler;
//import com.samsamgyeesam.studyingvally.domain.user.service.AuthUserDetailsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
///**
// * Spring Security 설정 클래스이다.
// *
// * 현재 프로젝트에서는
// * - 로그인
// * - 로그아웃
// * - 권한별 성공 경로 분기
// * - 정적 리소스(css, js, image) Security 제외
// *
// * 를 담당한다.
// */
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    /**
//     * 로그인 시 사용자 인증 정보를 조회하는 서비스
//     */
//    private final AuthUserDetailsService authUserDetailsService;
//
//    /**
//     * 로그인 성공 후 권한별 이동 경로를 분기하는 핸들러
//     */
//    private final AuthSuccessHandler authSuccessHandler;
//
//    /*
//     * 현재 프로젝트는 평문 비밀번호 비교 방식으로 테스트 중이다.
//     *
//     * 주의:
//     * 실무에서는 사용하면 안 되고,
//     * 현재는 부트캠프 팀 프로젝트 진행 단계에 맞춘 임시 설정이다.
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    /*
//     * 정적 리소스에 대한 요청은 Security 인증 대상에서 제외한다.
//     *
//     * PathRequest.toStaticResources().atCommonLocations() 는
//     * /static 하위의 css, js, images 등의 공통 정적 리소스를 대상으로 한다.
//     */
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }
//
//    /**
//     * Security 필터 체인 설정
//     *
//     * @param http HttpSecurity 객체
//     * @return SecurityFilterChain
//     * @throws Exception 설정 중 예외
//     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                /**
//                 * 사용자 인증 정보 조회 서비스 등록
//                 */
//                .userDetailsService(authUserDetailsService)
//
//                /**
//                 * URL별 접근 권한 설정
//                 *
//                 * 주의:
//                 * css/js/image는 위 WebSecurityCustomizer에서 제외했으므로
//                 * 여기서는 따로 permitAll로 작성하지 않는다.
//                 */
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/",
//                                "/main",
//                                "/auth/login",
//                                "/auth/signup1",
//                                "/auth/find",
//                                "/auth/findid",
//                                "/auth/findid2",
//                                "/auth/findpw1",
//                                "/auth/findpw2",
//                                "/image/**"
//                                // 이미지를 넣은 이유는 정적 리스소 경로 처리하는데 images는 처리하지만 image는 예외처리에서 빠질 수 있다함
//                                // 그래서 js, css는 뺐지만 image만 따로 넣어줌
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                )
//
//                /**
//                 * 로그인 설정
//                 */
//                .formLogin(login -> login
//                        .loginPage("/auth/login")
//                        .loginProcessingUrl("/auth/login")
//                        .usernameParameter("loginId")
//                        .passwordParameter("password")
//                        .successHandler(authSuccessHandler)
//                        .failureUrl("/auth/login?error=true")
//                )
//
//                /**
//                 * 로그아웃 설정
//                 */
//                .logout(logout -> logout
//                        .logoutUrl("/auth/logout")
//                        .logoutSuccessUrl("/main")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                )
//
//                /**
//                 * 학습 단계에서는 CSRF를 임시 비활성화한다.
//                 */
//                .csrf(csrf -> csrf.disable());
//
//        return http.build();
//    }
//}

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
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable());

        return http.build();
    }

}