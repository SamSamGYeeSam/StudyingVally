package com.samsamgyeesam.studyingvally.domain.notice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "com.samsamgyeesam.studyingvally.domain.notice.controller")
public class NoticeExceptionHandler {

    @ExceptionHandler(NoticeException.class)
    public String handleNoticeException(NoticeException e, Model model) {
        log.error("[공지 도메인 에러] {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "notice/notice_error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        log.error("[시스템 오류] {}", e.getMessage(), e);
        model.addAttribute("errorMessage", "시스템 처리 중 오류가 발생했습니다.");
        return "notice/notice_error";
    }
}