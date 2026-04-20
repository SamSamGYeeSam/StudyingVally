package com.samsamgyeesam.studyingvally.domain.admin.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 관리자 기능 전용 전역 예외 처리 클래스
 *
 * 왜 필요한가:
 * - 관리자 컨트롤러마다 try-catch를 반복하지 않고
 *   한 곳에서 예외를 처리하기 위함이다.
 *
 * 동작 방식:
 * - AdminException 발생 시 사용자에게 메시지를 보여주는 에러 페이지로 이동
 * - 예상하지 못한 시스템 예외는 공통 메시지로 처리
 */
@Slf4j
@ControllerAdvice(basePackages = "com.samsamgyeesam.studyingvally.domain.admin")
public class AdminExceptionHandler {

    /**
     * 관리자 비즈니스 예외 처리
     *
     * @param e 발생한 관리자 예외
     * @param model 뷰 전달 객체
     * @return 관리자 에러 페이지
     */
    @ExceptionHandler(AdminException.class)
    public String handleAdminException(AdminException e, Model model) {
        log.error("[관리자 비즈니스 예외] {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "admin/admin_error";
    }

    /**
     * 예상하지 못한 시스템 예외 처리
     *
     * @param e 발생한 예외
     * @param model 뷰 전달 객체
     * @return 관리자 에러 페이지
     */
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        log.error("[관리자 시스템 예외] {}", e.getMessage(), e);
        model.addAttribute("errorMessage", "시스템 처리 중 오류가 발생했습니다.");
        return "admin/admin_error";
    }
}