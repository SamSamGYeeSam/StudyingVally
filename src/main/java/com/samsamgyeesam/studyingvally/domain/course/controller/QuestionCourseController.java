package com.samsamgyeesam.studyingvally.domain.course.controller;

import com.samsamgyeesam.studyingvally.domain.course.dto.QuestionCourseDTO;
import com.samsamgyeesam.studyingvally.domain.course.service.QuestionCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher/course")
public class QuestionCourseController {

    private final QuestionCourseService questionService;

    // 질문 목록 조회
    // 강의 목록에서 특정 강의 선택 후 질문 보기 -> post
    // 답변 등록 후 그 화면으로 리다이렉트 -> get
    @RequestMapping("/question")
    public String gotoQuestionListPage(@RequestParam Long courseId, Model model) {
        List<QuestionCourseDTO> questions = questionService.findQuestionsByCourseId(courseId);

        model.addAttribute("questions", questions);
        model.addAttribute("courseId", courseId);

        return "course/questionlist";
    }

    // 질문에 답변 페이지로 이동하기
    @PostMapping("/question/answerPage")
    public String gotoQuestionAnswerPage(@RequestParam Long questionCourseNo,
                                         @RequestParam Long courseId,
                                         Model model) {

        QuestionCourseDTO question = questionService.findQuestionById(questionCourseNo);

        model.addAttribute("question", question);
        model.addAttribute("courseId", courseId);

        return "course/answerquestion";

    }

    // 답변 등록 처리
    @PostMapping("/question/answer")
    public String answerQuestion(@RequestParam Long questionCourseNo,
                                 @RequestParam Long courseId,
                                 @RequestParam String questionCourseAnswer,
                                 RedirectAttributes redirectAttributes) {

        questionService.answerQuestion(questionCourseNo, questionCourseAnswer);

        redirectAttributes.addFlashAttribute("successMessage", "답변이 등록되었습니다.");
        redirectAttributes.addAttribute("courseId", courseId);

        return "redirect:/teacher/course/question";
    }

}
