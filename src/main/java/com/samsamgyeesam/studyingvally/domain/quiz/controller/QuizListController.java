package com.samsamgyeesam.studyingvally.domain.quiz.controller;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizListDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.service.QuizService; // 서비스 import 필요
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class QuizListController {

    private final QuizService quizService; // 추가: 서비스 주입

    //퀴즈리스트 생성하는 항목. 퀴즈 아이디를 받아 테이블 생성
    @PostMapping("/teacher/quiz/create-quizlist")
    public ModelAndView registQuizList(@ModelAttribute QuizListDTO quizListDTO, ModelAndView mv) {
        // 1. DTO 확인
        System.out.println(quizListDTO);

        // 2. 서비스 호출하여 DB에 저장 (추가된 부분)
        Integer result = quizService.registQuizList(quizListDTO);

        // 3. 결과에 따른 처리 (로그나 분기 처리 가능)
        if(result > 0) {
            mv.addObject("message","저장 성공 : " + result);
        } else {
            mv.addObject("message","저장 실패!");
        }

        //추후 정해야함.
        mv.setViewName("/quiz/registquizlist");

        return mv;
    }
}