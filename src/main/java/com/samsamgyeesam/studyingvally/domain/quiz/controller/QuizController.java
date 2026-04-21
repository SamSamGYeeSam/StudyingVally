package com.samsamgyeesam.studyingvally.domain.quiz.controller;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizChapterDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizListDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizListFormDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"courseId", "chapNo", "quizNo", "quizListNo"})
public class QuizController {

    private final QuizService quizService;

    // ==========================================
    // 1. 선택한 강의의 챕터 보기
    // ==========================================
    @PostMapping("/teacher/quiz/course-post")
    public String findQuizListPost(@RequestParam(name = "courseId") Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "redirect:/teacher/quiz/course";
    }

    @GetMapping("/teacher/quiz/course")
    public String findQuizList(@ModelAttribute("courseId") Long courseId, Model model) {
        List<QuizChapterDTO> quizChapterList = quizService.getQuizChapterListByCourseId(courseId);
        model.addAttribute("quizChapterList", quizChapterList);
        return "quiz/quizchapterlist";
    }

    // ==========================================
    // 2. 챕터별 퀴즈 목록 보기
    // ==========================================
    @PostMapping("/teacher/quiz/find-quiz-by-chap-post")
    public String findQuizByChapPost(@RequestParam(name = "chapNo") Long chapNo, Model model) {
        model.addAttribute("chapNo", chapNo);
        return "redirect:/teacher/quiz/find-quiz-by-chap";
    }

    @GetMapping("/teacher/quiz/find-quiz-by-chap")
    public String findQuizByChap(@ModelAttribute("chapNo") Long chapNo, Model model) {
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
    public String findRegistQuizFormPost(@RequestParam(name = "chapNo", required = false) Long chapNo, Model model) {
        model.addAttribute("chapNo", chapNo);
        return "redirect:/teacher/chapter/regist-quiz-form";
    }

    @GetMapping("/teacher/chapter/regist-quiz-form")
    public String findRegistQuizForm(@ModelAttribute("chapNo") Long chapNo) {
        return "quiz/registquiz";
    }

    @PostMapping("/teacher/chapter/create-quiz")
    public String registQuiz(@ModelAttribute QuizDTO quizDTO, RedirectAttributes redirectAttributes) {
        try {
            quizService.registQuiz(quizDTO);
            redirectAttributes.addFlashAttribute("successMessage", "퀴즈가 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "퀴즈 생성 중 오류가 발생했습니다.");
        }
        return "redirect:/teacher/quiz/find-quiz-by-chap";
    }

    // ==========================================
    // 5. 퀴즈 문제들 "일괄" 만들기 (폼 & 처리)
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

    @PostMapping("/teacher/quiz/create-quizlist-batch")
    public String createQuizListBatch(@ModelAttribute QuizListFormDTO formDTO, RedirectAttributes redirectAttributes) {
        try {
            String quizNo = formDTO.getQuizNo();
            List<QuizListDTO> dtoList = formDTO.getQuizList();

            if (dtoList == null || dtoList.isEmpty()) {
                throw new IllegalArgumentException("등록할 문제가 하나도 없습니다.");
            }

            for (QuizListDTO dto : dtoList) {
                dto.setQuizNo(quizNo);
            }

            quizService.registQuizListBatch(dtoList);

            redirectAttributes.addFlashAttribute("successMessage", "문제가 성공적으로 일괄 등록되었습니다!");
            return "redirect:/teacher/quiz/find-quizlist-by-quiz";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/teacher/quiz/regist-quizlist-form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "문제 등록 중 서버 오류가 발생했습니다.");
            return "redirect:/teacher/quiz/regist-quizlist-form";
        }
    }

    // ==========================================
    // 6. 퀴즈 문제 수정 (폼 & 처리)
    // ==========================================
    @PostMapping("/teacher/quiz/update-quizlist-form-post")
    public String findUpdateQuizListFormPost(@RequestParam(name = "quizNo") String quizNo, Model model) {
        model.addAttribute("quizNo", quizNo);
        return "redirect:/teacher/quiz/update-quizlist-form";
    }

    @GetMapping("/teacher/quiz/update-quizlist-form")
    public String findUpdateQuizListForm(@ModelAttribute("quizNo") String quizNo, Model model) {
        List<QuizListDTO> quizItems = quizService.getQuizListItemsByQuizNo(quizNo);
        model.addAttribute("quizItems", quizItems);
        model.addAttribute("quizNo", quizNo);
        return "quiz/edit_quizlist";
    }

    @PostMapping("/teacher/quiz/update-quizlist-batch")
    public String updateQuizListBatch(@ModelAttribute QuizListFormDTO formDTO, RedirectAttributes redirectAttributes) {
        try {
            List<QuizListDTO> dtoList = formDTO.getQuizList();
            String quizNo = formDTO.getQuizNo();

            for (QuizListDTO dto : dtoList) {
                dto.setQuizNo(quizNo);
            }
            quizService.updateQuizListBatch(dtoList);

            redirectAttributes.addFlashAttribute("successMessage", "모든 문제가 성공적으로 수정되었습니다!");
            return "redirect:/teacher/quiz/find-quizlist-by-quiz";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/teacher/quiz/update   -quizlist-form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "수정 중 서버 오류가 발생했습니다.");
            return "redirect:/teacher/quiz/update-quizlist-form";
        }
    }

}