package com.samsamgyeesam.studyingvally.domain.quiz.controller;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizListDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/teacher/quiz/create-quizlist")
    public String registQuizListForm() {
        return "quiz/registquizlist";
    }

    @PostMapping("/teacher/quiz/create-quizlist")
    public ModelAndView registQuizList(@ModelAttribute QuizListDTO quizListDTO, ModelAndView mv) {

        Integer result = quizService.registQuizList(quizListDTO);

        if (result > 0) {
            mv.addObject("message", "저장 성공 : " + result);
        } else {
            mv.addObject("message", "저장 실패!");
        }

        mv.setViewName("/quiz/registquizlist");

        return mv;
    }
}
