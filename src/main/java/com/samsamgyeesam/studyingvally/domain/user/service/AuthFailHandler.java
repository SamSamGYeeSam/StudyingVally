package com.samsamgyeesam.studyingvally.domain.user.service;

import com.samsamgyeesam.studyingvally.domain.user.entity.LoginLog;
import com.samsamgyeesam.studyingvally.domain.user.repository.LoginLogRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import jakarta.servlet.http.HttpSession;

/*
 * 로그인 실패 시 처리하는 핸들러
 *
 * 처리 내용:
 * 1. 비밀번호가 틀렸을 경우 로그인 실패 횟수 증가
 * 2. 계정 잠김 상태일 경우 잠김 메시지 반환
 * 3. 로그인 실패 로그 저장
 * 4. 로그인 화면으로 다시 이동
 */
@Component
@RequiredArgsConstructor
public class AuthFailHandler extends SimpleUrlAuthenticationFailureHandler {

    // 사용자 관련 서비스
    private final UserService userService;

    // 로그인 성공/실패 로그 저장용 Repository
    private final LoginLogRepository loginLogRepository;

    /*
     * 로그인 실패 시 실행되는 메서드
     *
     * @param request 로그인 요청 객체
     * @param response 응답 객체
     * @param exception 인증 실패 예외
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        // 로그인 폼에서 입력한 아이디
        String loginId = request.getParameter("loginId");

        // 접속 IP 주소
        String ipAddress = request.getRemoteAddr();

        // 화면에 보여줄 에러 메시지
        String errorMessage;

        /*
         * 비밀번호 불일치 또는 아이디 없음
         * -> 실패 횟수 증가
         */
        if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.";

            if (loginId != null && !loginId.isBlank()) {
                userService.incrementLoginFailCount(loginId);
            }

            /*
             * 계정 잠금 상태
             * -> 잠금 메시지 반환
             */
        } else if (exception instanceof LockedException) {
            errorMessage = "로그인 5회 실패로 계정이 잠겼습니다. 관리자에게 문의하세요.";

            // 그 외 예외
        } else if (exception instanceof DisabledException) {
        errorMessage = "비활성화된 계정입니다. 관리자에게 문의해주세요.";

        }else {
            errorMessage = "로그인 요청을 처리할 수 없습니다.";
        }

        // 로그인 실패 로그 저장
        loginLogRepository.save(
                new LoginLog(
                        loginId,              // 로그인 시도 아이디
                        LocalDateTime.now(),  // 로그인 시각
                        false,                // 로그인 성공 여부
                        ipAddress             // 접속 IP
                )
        );

        HttpSession session = request.getSession();
        session.setAttribute("loginErrorMessage", errorMessage);

        // 로그인 페이지로 다시 이동
        setDefaultFailureUrl("/auth/login");

        super.onAuthenticationFailure(request, response, exception);
    }
}