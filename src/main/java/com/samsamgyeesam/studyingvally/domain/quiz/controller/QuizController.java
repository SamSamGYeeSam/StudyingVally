package com.samsamgyeesam.studyingvally.domain.quiz.controller;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizListDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;



    //퀴즈 등록 시 강의



    //퀴즈 등록 get, post
    @GetMapping("/teacher/quiz/create-quizlist")
    public String registQuizListForm()
    {
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


    @GetMapping("/teacher/quiz/create-quiz")
    public String registQuizForm(@RequestParam(name = "chap_no", required = false) Integer chapNo, Model model) {
        // url 파라미터로 들어온 chap_no를 뷰(html)로 전달
        model.addAttribute("chapNo", chapNo);
        return "quiz/registquiz";
    }

    // 퀴즈 등록 post
    @PostMapping("/teacher/quiz/create-quiz")
    public ModelAndView registQuiz(@ModelAttribute QuizDTO quizDTO, ModelAndView mv) {

        Integer result = quizService.registQuiz(quizDTO);

        if (result > 0) {
            mv.addObject("message", "퀴즈 저장 성공! 퀴즈 번호 : " + result);
        } else {
            mv.addObject("message", "퀴즈 저장 실패!");
        }

        // 등록 후 다시 같은 폼 화면을 보여주거나, 목록으로 리다이렉트 할 수 있습니다.
        mv.setViewName("quiz/registquiz");

        return mv;
    }
}
