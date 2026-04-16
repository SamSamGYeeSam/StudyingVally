package com.samsamgyeesam.studyingvally.global.Config;

import com.samsamgyeesam.studyingvally.domain.user.service.AuthSuccessHandler;
import com.samsamgyeesam.studyingvally.domain.user.service.AuthUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //로그인 시 사용자 인증 정보를 조회하는 서비스
    private final AuthUserDetailsService authUserDetailsService;

    //로그인 성공 후 권한별 이동 경로를 분기하는 핸들러
    private final AuthSuccessHandler authSuccessHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/main",
                                "/auth/login",
                                "/auth/signup1",
                                "/auth/signup2",
                                "/auth/signup",
                                "/auth/signuptype",
                                "/auth/find",
                                "/auth/findid",
                                "/auth/findid2",
                                "/auth/findpw1",
                                "/auth/findpw2",
                                "/image/**"
                                // 이미지를 넣은 이유는 정적 리스소 경로 처리하는데 images는 처리하지만 image는 예외처리에서 빠질 수 있다함
                                // 그래서 js, css는 뺐지만 image만 따로 넣어줌
                        ).permitAll()
                        .anyRequest().authenticated()
                )


                // 로그인 설정
                .formLogin(login -> login
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/auth/login")
                                .usernameParameter("loginId")
                                .passwordParameter("password")
//                        .successHandler(authSuccessHandler)
                                .failureUrl("/auth/login?error=true")
                );

        return http.build();
    }

}