package com.samsamgyeesam.studyingvally.domain.admin.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice(basePackages = "com.samsamgyeesam.studyingvally.domain.admin")
public class AdminExceptionHandler {


    @ExceptionHandler(AdminException.class)
    public String handleAdminException(AdminException e, Model model) {
        log.error("[관리자 비즈니스 예외] {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "admin/admin_error";
    }


    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        log.error("[관리자 시스템 예외] {}", e.getMessage(), e);
        model.addAttribute("errorMessage", "시스템 처리 중 오류가 발생했습니다.");
        return "admin/admin_error";
    }
}