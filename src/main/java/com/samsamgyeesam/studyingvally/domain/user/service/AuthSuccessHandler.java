package com.samsamgyeesam.studyingvally.domain.user.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

/*
 * 로그인 성공 후 권한별 이동 경로를 분기하는 핸들러이다.
 *
 * 현재 팀 프로젝트 기준 경로:
 * - 강사 : /course/teachermain
 * - 학생 : /student/main
 * - 관리자 : /main
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 로그인 성공 시 권한을 확인한 뒤 각 경로로 이동시킨다.
     *
     * @param request 요청 객체
     * @param response 응답 객체
     * @param authentication 인증 객체
     * @throws IOException 입출력 예외
     * @throws ServletException 서블릿 예외
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        /*
         * 현재 로그인한 사용자의 권한 목록을 가져온다.
         */
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        /*
         * 강사 권한이면 강사 메인페이지 경로로 이동한다.
         */
        if (hasRole(authorities, "ROLE_TEACHER")) {
            response.sendRedirect("/course/teachermain");
            return;
        }

        /*
         * 학생 권한이면 학생 메인페이지 경로로 이동한다.
         */
        if (hasRole(authorities, "ROLE_STUDENT")) {
            response.sendRedirect("/student/main");
            return;
        }

        /*
         * 관리자 권한이면 아직 관리자 메인페이지가 없으므로 공용 /main 으로 이동한다.
         */
        if (hasRole(authorities, "ROLE_ADMIN")) {
            response.sendRedirect("/admin/main");
            return;
        }

        /*
         * 혹시 어떤 권한에도 해당하지 않으면 기본적으로 /main 으로 이동한다.
         */
        response.sendRedirect("/main");
    }

    /*
     * 권한 목록 안에 특정 권한이 있는지 확인한다.
     *
     * @param authorities 현재 로그인한 사용자의 권한 목록
     * @param role 확인할 권한 문자열
     * @return 권한 포함 여부
     */
    private boolean hasRole(Collection<? extends GrantedAuthority> authorities, String role) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals(role));
    }
}