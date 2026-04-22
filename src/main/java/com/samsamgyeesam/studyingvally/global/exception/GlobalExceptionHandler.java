package com.samsamgyeesam.studyingvally.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.security.access.AccessDeniedException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 1. NullPointerException 처리
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 상태 코드 반환
    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException e, Model model) {
        log.error("NullPointerException 발생: ", e);
        model.addAttribute("errorMessage", "데이터를 불러오는 중 문제가 발생했습니다. (Null 참조 오류)");
        return "error/error";
    }

    /**
     * 2. IllegalArgumentException 처리
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 상태 코드 반환
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        log.error("IllegalArgumentException 발생: ", e);
        model.addAttribute("errorMessage", "잘못된 요청입니다. 입력값을 확인해 주세요.");
        return "error/error";
    }

    /**
     * 3. 403 Forbidden (권한 없음) 에러 처리
     * 주로 관리자 페이지에 일반 유저가 접근하려 할 때 등 권한이 없을 때 발생합니다.
     */
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403 상태 코드 반환
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException e, Model model) {
        log.error("403 AccessDeniedException 발생: ", e);
        model.addAttribute("errorMessage", "해당 페이지에 접근할 권한이 없습니다.");
        return "error/error";
    }

    /**
     * 4. 500 Internal Server Error (서버 내부 에러) 및 그 외 모든 예외 (Catch-all)
     * 위에서 미리 잡아내지 못한 모든 예외는 서버 에러(500)로 간주하고 여기서 처리합니다.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 상태 코드 반환
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        log.error("500 최상위 Exception 발생: ", e);
        model.addAttribute("errorMessage", "서버 내부에서 예기치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
        return "error/error";
    }
}