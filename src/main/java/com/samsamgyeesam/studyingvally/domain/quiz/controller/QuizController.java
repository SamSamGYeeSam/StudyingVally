package com.samsamgyeesam.studyingvally.domain.quiz.controller;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizChapterDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizListDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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


    @PostMapping("/teacher/quiz/create-quiz-form")
    public String registQuizForm(@RequestParam(name = "chapNo", required = false) Integer chapNo, Model model) {

        // 넘어온 챕터 번호를 확인 후 뷰로 전달
        model.addAttribute("chapNo", chapNo);

        return "quiz/registquiz";
    }

    // 2. 퀴즈 실제 DB 저장 (POST 방식)
    @PostMapping("/teacher/quiz/create-quiz")
    public ModelAndView registQuiz(@ModelAttribute QuizDTO quizDTO, ModelAndView mv) {

        Integer result = quizService.registQuiz(quizDTO);

        if (result > 0) {
            mv.addObject("message", "퀴즈 저장 성공! 퀴즈 번호 : " + result);
        } else {
            mv.addObject("message", "퀴즈 저장 실패!");
        }

        // 등록 후 다시 등록 폼을 보여주기 위해 세팅
        mv.setViewName("quiz/registquiz");

        return mv;
    }

    // 1. 챕터 등록 폼 화면 진입 (POST)
    @PostMapping("/create-form")
    public String registQuizChapterForm(@RequestParam(name = "courseId", required = false) Integer courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "quiz/registquizchapter"; // 파일명/경로도 quiz 하위로 맞췄습니다.
    }


    // 3. 챕터 목록 조회 (GET)
    @GetMapping("/teacher/quiz/course")
    public String getQuizChapterList(@RequestParam(name = "courseId") Integer courseId, Model model) {

        List<QuizChapterDTO> quizChapterList = quizService.getQuizChapterListByCourseId(courseId);

        model.addAttribute("quizChapterList", quizChapterList);
        model.addAttribute("courseId", courseId);

        return "quiz/quizchapterlist";
    }
}
