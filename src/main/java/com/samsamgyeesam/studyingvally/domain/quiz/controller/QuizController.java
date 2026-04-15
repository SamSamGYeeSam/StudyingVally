package com.samsamgyeesam.studyingvally.domain.quiz.controller;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizChapterDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizListDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
// 👇 강사 측 폼 유지 및 리다이렉트를 위한 필수 세션 키들
@SessionAttributes({"courseId", "chapNo", "quizNo", "quizListNo"})
public class QuizController {

    private final QuizService quizService;

    // ==========================================
    // 1. 선택한 강의의 챕터 보기
    // ==========================================
    @PostMapping("/teacher/quiz/course-post")
    public String findQuizListPost(@RequestParam(name = "courseId") Long courseId, Model model) { // Long 변경
        model.addAttribute("courseId", courseId);
        return "redirect:/teacher/quiz/course";
    }

    @GetMapping("/teacher/quiz/course")
    public String findQuizList(@ModelAttribute("courseId") Long courseId, Model model) { // Long 변경
        List<QuizChapterDTO> quizChapterList = quizService.getQuizChapterListByCourseId(courseId);
        model.addAttribute("quizChapterList", quizChapterList);
        return "quiz/quizchapterlist";
    }

    // ==========================================
    // 2. 챕터별 퀴즈 목록 보기
    // ==========================================
    @PostMapping("/teacher/quiz/find-quiz-by-chap-post")
    public String findQuizByChapPost(@RequestParam(name = "chapNo") Long chapNo, Model model) { // Long 변경
        model.addAttribute("chapNo", chapNo);
        return "redirect:/teacher/quiz/find-quiz-by-chap";
    }

    @GetMapping("/teacher/quiz/find-quiz-by-chap")
    public String findQuizByChap(@ModelAttribute("chapNo") Long chapNo, Model model) { // Long 변경
        List<QuizDTO> quizList = quizService.getQuizListByChapNo(chapNo);
        model.addAttribute("quizList", quizList);
        return "quiz/quiz_list_by_chap";
    }

    // ==========================================
    // 3. 퀴즈별 문제 목록 보기
    // ==========================================
    @PostMapping("/teacher/quiz/find-quizlist-by-quiz-post")
    public String findQuizListByQuizPost(@RequestParam(name = "quizNo") String quizNo, Model model) {
        model.addAttribute("quizNo", quizNo);
        return "redirect:/teacher/quiz/find-quizlist-by-quiz";
    }

    @GetMapping("/teacher/quiz/find-quizlist-by-quiz")
    public String findQuizListByQuiz(@ModelAttribute("quizNo") String quizNo, Model model) {
        List<QuizListDTO> quizItems = quizService.getQuizListItemsByQuizNo(quizNo);
        model.addAttribute("quizItems", quizItems);
        return "quiz/quizlist_items";
    }

    // ==========================================
    // 4. 퀴즈 만들기 (폼 & 처리)
    // ==========================================
    @PostMapping("/teacher/chapter/regist-quiz-form-post")
    public String findRegistQuizFormPost(@RequestParam(name = "chapNo", required = false) Long chapNo, Model model) { // Long 변경
        model.addAttribute("chapNo", chapNo);
        return "redirect:/teacher/chapter/regist-quiz-form";
    }

    @GetMapping("/teacher/chapter/regist-quiz-form")
    public String findRegistQuizForm(@ModelAttribute("chapNo") Long chapNo) { // Long 변경
        return "quiz/registquiz";
    }

    @PostMapping("/teacher/chapter/create-quiz")
    public String registQuiz(@ModelAttribute QuizDTO quizDTO) {
        quizService.registQuiz(quizDTO);
        return "redirect:/teacher/quiz/find-quiz-by-chap";
    }

    // ==========================================
    // 5. 퀴즈 문제들 만들기 (폼 & 처리)
    // ==========================================
    @PostMapping("/teacher/quiz/regist-quizlist-form-post")
    public String findRegistQuizListFormPost(@RequestParam(name = "quizNo", required = false) String quizNo, Model model) {
        model.addAttribute("quizNo", quizNo);
        return "redirect:/teacher/quiz/regist-quizlist-form";
    }

    @GetMapping("/teacher/quiz/regist-quizlist-form")
    public String findRegistQuizListForm(@ModelAttribute("quizNo") String quizNo) {
        return "quiz/registquizlist";
    }

    @PostMapping("/teacher/quiz/create-quizlist")
    public String registQuizList(@ModelAttribute QuizListDTO quizListDTO) {
        quizService.registQuizList(quizListDTO);
        return "redirect:/teacher/quiz/find-quizlist-by-quiz";
    }

    // ==========================================
    // 6. 퀴즈 문제 수정 (폼 & 처리)
    // ==========================================
    @PostMapping("/teacher/quiz/update-quizlist-form-post")
    public String findUpdateQuizListFormPost(@RequestParam(name = "quizListNo") Long quizListNo, Model model) { // Long 변경
        model.addAttribute("quizListNo", quizListNo);
        return "redirect:/teacher/quiz/update-quizlist-form";
    }

    @GetMapping("/teacher/quiz/update-quizlist-form")
    public String findUpdateQuizListForm(@ModelAttribute("quizListNo") Long quizListNo, Model model) { // Long 변경
        QuizListDTO quizListDTO = quizService.getQuizListItemById(quizListNo);
        model.addAttribute("quizListDTO", quizListDTO);
        return "quiz/edit_quizlist";
    }

    @PostMapping("/teacher/quiz/update-quizlist")
    public String updateQuizList(@ModelAttribute QuizListDTO quizListDTO, Model model) {
        quizService.updateQuizList(quizListDTO);
        // 업데이트 후 목록으로 갈 때 필요한 quizNo 갱신
        model.addAttribute("quizNo", quizListDTO.getQuizNo());
        return "redirect:/teacher/quiz/find-quizlist-by-quiz";
    }

    // ==========================================
    // 7. [TEST용] 임시 테스트 시작 페이지
    // ==========================================
    @GetMapping("/teacher/quiz/test-start")
    public String findTestStartPage() {
        return "quiz/test_start";
    }
}