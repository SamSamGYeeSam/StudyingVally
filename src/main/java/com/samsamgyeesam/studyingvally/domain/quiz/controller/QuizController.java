package com.samsamgyeesam.studyingvally.domain.quiz.controller;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizChapterDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizListDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizListFormDTO; // ✨ 필수: 폼 데이터를 리스트로 받을 바구니 DTO
import com.samsamgyeesam.studyingvally.domain.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String registQuiz(@ModelAttribute QuizDTO quizDTO) {
        quizService.registQuiz(quizDTO);
        return "redirect:/teacher/quiz/find-quiz-by-chap";
    }

    // ==========================================
    // 5. 퀴즈 문제들 "일괄" 만들기 (폼 & 처리) ✨ 수정된 부분 ✨
    // ==========================================
    @PostMapping("/teacher/quiz/regist-quizlist-form-post")
    public String findRegistQuizListFormPost(@RequestParam(name = "quizNo", required = false) String quizNo, Model model) {
        model.addAttribute("quizNo", quizNo);
        return "redirect:/teacher/quiz/regist-quizlist-form";
    }

    @GetMapping("/teacher/quiz/regist-quizlist-form")
    public String findRegistQuizListForm(@ModelAttribute("quizNo") String quizNo) {
        return "quiz/registquizlist"; // 이 화면이 앞서 수정한 일괄 등록 HTML 화면입니다.
    }

    // 👇 404 에러의 주범이었던 메서드를 다건 등록(batch) 전용으로 교체했습니다!
    @PostMapping("/teacher/quiz/create-quizlist-batch")
    public String createQuizListBatch(@ModelAttribute QuizListFormDTO formDTO, RedirectAttributes rttr) {
        try {
            String quizNo = formDTO.getQuizNo();
            List<QuizListDTO> dtoList = formDTO.getQuizList();

            if (dtoList == null || dtoList.isEmpty()) {
                throw new IllegalArgumentException("등록할 문제가 하나도 없습니다.");
            }

            // 문제 배열 각각에 부모 퀴즈 번호(quizNo)를 심어줍니다.
            for (QuizListDTO dto : dtoList) {
                dto.setQuizNo(quizNo);
            }

            // 서비스로 넘겨 100점 만점 검증 및 DB 저장을 수행합니다.
            quizService.registQuizListBatch(dtoList);

            // 성공 시 알림 메시지를 가지고 퀴즈 문제 목록 화면으로 리다이렉트합니다.
            rttr.addFlashAttribute("successMessage", "문제가 성공적으로 일괄 등록되었습니다!");
            return "redirect:/teacher/quiz/find-quizlist-by-quiz";

        } catch (IllegalArgumentException e) {
            // 배점이 100점이 아니어서 튕겼을 때 에러 메시지를 가지고 원래 폼으로 돌아갑니다.
            rttr.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/teacher/quiz/regist-quizlist-form";
        } catch (Exception e) {
            rttr.addFlashAttribute("errorMessage", "문제 등록 중 서버 오류가 발생했습니다.");
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

    // 2) quizNo에 속한 '모든' 문제를 불러와서 모델에 담습니다.
    @GetMapping("/teacher/quiz/update-quizlist-form")
    public String findUpdateQuizListForm(@ModelAttribute("quizNo") String quizNo, Model model) {
        List<QuizListDTO> quizItems = quizService.getQuizListItemsByQuizNo(quizNo);
        model.addAttribute("quizItems", quizItems);
        // HTML에서 부모 quizNo를 보관하기 위해 담아줍니다.
        model.addAttribute("quizNo", quizNo);
        return "quiz/edit_quizlist";
    }

    // 3) 100점 검증과 일괄 수정을 처리합니다.
    @PostMapping("/teacher/quiz/update-quizlist-batch")
    public String updateQuizListBatch(@ModelAttribute QuizListFormDTO formDTO, RedirectAttributes rttr) {
        try {
            List<QuizListDTO> dtoList = formDTO.getQuizList();
            String quizNo = formDTO.getQuizNo();

            // 부모 번호 주입 및 일괄 수정 서비스 호출
            for (QuizListDTO dto : dtoList) {
                dto.setQuizNo(quizNo);
            }
            quizService.updateQuizListBatch(dtoList);

            rttr.addFlashAttribute("successMessage", "모든 문제가 성공적으로 수정되었습니다!");
            return "redirect:/teacher/quiz/find-quizlist-by-quiz";

        } catch (IllegalArgumentException e) {
            rttr.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/teacher/quiz/update-quizlist-form";
        } catch (Exception e) {
            rttr.addFlashAttribute("errorMessage", "수정 중 서버 오류가 발생했습니다.");
            return "redirect:/teacher/quiz/update-quizlist-form";
        }
    }

    // ==========================================
    // 7. [TEST용] 임시 테스트 시작 페이지
    // ==========================================
    @GetMapping("/teacher/quiz/test-start")
    public String findTestStartPage() {
        return "quiz/test_start";
    }

}