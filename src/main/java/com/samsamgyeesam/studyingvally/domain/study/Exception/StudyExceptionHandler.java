package com.samsamgyeesam.studyingvally.domain.study.Exception;

import com.samsamgyeesam.studyingvally.domain.course.exception.CourseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "com.samsamgyeesam.studyingvally.domain.npc.controller")
public class StudyExceptionHandler {

    @ExceptionHandler(CourseException.class)
    public String handleStudyException(CourseException e, Model model) {
        log.error("[스터디 도메인 에러] {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "study/study_error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        log.error("[시스템 오류] {}", e.getMessage(), e);
        model.addAttribute("errorMessage", "시스템 처리 중 오류가 발생했습니다.");
        return "study/study_error";
    }

}
