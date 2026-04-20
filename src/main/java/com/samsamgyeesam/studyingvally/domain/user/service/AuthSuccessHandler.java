package com.samsamgyeesam.studyingvally.domain.user.service;

import com.samsamgyeesam.studyingvally.domain.user.entity.LoginLog;
import com.samsamgyeesam.studyingvally.domain.user.repository.LoginLogRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;

/*
 * 로그인 성공 후 처리 핸들러
 *
 * 처리 내용:
 * 1. 로그인 성공 시 로그인 실패 횟수 초기화
 * 2. 로그인 성공 로그 저장
 * 3. 사용자 권한에 따라 메인 화면 분기
 */
@Component
@RequiredArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    // 사용자 관련 서비스
    private final UserService userService;

    // 로그인 성공/실패 로그 저장용 Repository
    private final LoginLogRepository loginLogRepository;

    /*
     * 로그인 성공 시 실행되는 메서드
     *
     * @param request 로그인 요청 객체
     * @param response 응답 객체
     * @param authentication 인증 완료된 사용자 정보
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 현재 로그인한 사용자 아이디
        String loginId = authentication.getName();

        // 현재 접속 IP 주소
        String ipAddress = request.getRemoteAddr();

        // 로그인 성공 시 실패 횟수 초기화
        userService.resetLoginFailCount(loginId);

        // 로그인 성공 로그 저장
        loginLogRepository.save(
                new LoginLog(
                        loginId,              // 로그인한 사용자 아이디
                        LocalDateTime.now(),  // 로그인 시각
                        true,                 // 로그인 성공 여부
                        ipAddress             // 접속 IP
                )
        );

        // 현재 로그인한 사용자의 권한 목록
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // 강사 권한이면 강사 메인으로 이동
        if (hasRole(authorities, "ROLE_TEACHER")) {
            response.sendRedirect("/teacher/teachermain");
            return;
        }

        // 학생 권한이면 학생 메인으로 이동
        if (hasRole(authorities, "ROLE_STUDENT")) {
            response.sendRedirect("/student/main");
            return;
        }

        /* 관리자 권한이면 관리자 메인으로 이동 */
        if (hasRole(authorities, "ROLE_ADMIN")) {
            response.sendRedirect("/admin/main");
            return;
        }

        /* 그 외는 공통 메인으로 이동 */
        response.sendRedirect("/main");
    }

    /**
     * 현재 권한 목록에 특정 권한이 포함되어 있는지 확인하는 메서드
     *
     * @param authorities 현재 로그인 사용자의 권한 목록
     * @param role 확인할 권한 문자열
     * @return 포함 여부
     */
    private boolean hasRole(Collection<? extends GrantedAuthority> authorities, String role) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals(role));
    }
}