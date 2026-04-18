package com.samsamgyeesam.studyingvally.domain.course.controller;

import com.samsamgyeesam.studyingvally.domain.course.dto.QuestionCourseDTO;
import com.samsamgyeesam.studyingvally.domain.course.service.QuestionCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher/course/")
public class QuestionCourseController {

    private final QuestionCourseService questionService;

    // 질문 목록 조회
    @PostMapping("/question")
    public String viewQuestions(@RequestParam Long courseId, Model model) {
        List<QuestionCourseDTO> questions = questionService.findQuestionsByCourseId(courseId);

        model.addAttribute("questions", questions);
        model.addAttribute("courseId", courseId);

        return "course/questionlist";
    }

}
