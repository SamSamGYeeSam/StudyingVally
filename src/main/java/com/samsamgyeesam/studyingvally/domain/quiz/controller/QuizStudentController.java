package com.samsamgyeesam.studyingvally.domain.quiz.controller;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.*;
import com.samsamgyeesam.studyingvally.domain.quiz.service.QuizService;
import com.samsamgyeesam.studyingvally.domain.quiz.service.QuizStudentService;
import com.samsamgyeesam.studyingvally.domain.user.service.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
// 👇 세션 유지 항목에 quizNo 추가
@SessionAttributes({"courseId", "chapNo", "quizNo"})
public class QuizStudentController {

    private final QuizStudentService quizStudentService;
    private final QuizService quizService;

    // ==========================================
    // 1. 퀴즈방 입장하기
    // ==========================================
    @GetMapping("/student/quiz/room")
    public String findQuizRoom() {
        return "quiz/student-quiz/student_quiz_room";
    }

    // ==========================================
    // 2. 수강중인 강의 출력
    // ==========================================
    @GetMapping("/student/quiz/course")
    public String findQuizCourse(@AuthenticationPrincipal AuthUserDetails userDetails, Model model) {
        Long userNo = userDetails.getUserNo();
        List<QuizEnrolledCourseDTO> enrolledCourses = quizStudentService.getEnrolledCourses(userNo);
        model.addAttribute("enrolledCourses", enrolledCourses);
        model.addAttribute("displayName", userDetails.getDisplayName());
        return "quiz/student-quiz/student_course_list";
    }

    @PostMapping("/student/quiz/course-post")
    public String selectCoursePost(@RequestParam("courseId") Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "redirect:/student/quiz/select";
    }

    // ==========================================
    // 3. 선택한 강의의 챕터 목록 출력
    // ==========================================
    @GetMapping("/student/quiz/select")
    public String findQuizSelect(@ModelAttribute("courseId") Long courseId, Model model) {
        List<QuizChapterDTO> chapterList = quizService.getQuizChapterListByCourseId(courseId);
        model.addAttribute("chapterList", chapterList);
        return "quiz/student-quiz/student_chapter_list";
    }

    @PostMapping("/student/quiz/chapter-post")
    public String selectChapterPost(@RequestParam("chapNo") Long chapNo, Model model) {
        model.addAttribute("chapNo", chapNo);
        return "redirect:/student/quiz/list";
    }

    // ==========================================
    // 4. 선택한 챕터의 퀴즈 목록 출력
    // ==========================================
    @GetMapping("/student/quiz/list")
    public String findQuizList(@ModelAttribute("chapNo") Long chapNo, Model model) {
        List<QuizDTO> quizList = quizService.getQuizListByChapNo(chapNo);
        model.addAttribute("quizList", quizList);
        return "quiz/student-quiz/student_quiz_list";
    }

    // ==========================================
    // 5. 퀴즈 선택 및 실제 문제 풀기 화면 출력 (새로 추가)
    // ==========================================
    @PostMapping("/student/quiz/solve-post")
    public String selectQuizPost(@RequestParam("quizNo") String quizNo, Model model) {
        model.addAttribute("quizNo", quizNo); // 퀴즈 번호 세션 저장
        return "redirect:/student/quiz/solve"; // 문제 풀기 화면으로 리다이렉트
    }

    @GetMapping("/student/quiz/solve")
    public String solveQuiz(@ModelAttribute("quizNo") String quizNo, Model model) {
        // 해당 퀴즈의 상세 문제 리스트 조회
        List<QuizListDTO> quizItems = quizService.getQuizListItemsByQuizNo(quizNo);
        model.addAttribute("quizItems", quizItems);
        return "quiz/student-quiz/student_quiz_solve";
    }

    // 6. 퀴즈 완료 시 점수 저장 (AJAX용 API)
    // ==========================================
    @PostMapping("/student/quiz/submit-score")
    @ResponseBody // 화면을 반환하지 않고 데이터(문자열)만 반환
    public String submitScore(@RequestBody QuizAttemptDTO attemptDTO,
                              @AuthenticationPrincipal AuthUserDetails userDetails) {

        // Security 세션에서 현재 로그인한 학생의 PK를 꺼내 세팅
        attemptDTO.setUserNo(userDetails.getUserNo());

        // DB에 저장
        quizStudentService.saveQuizAttempt(attemptDTO);

        return "SUCCESS";
    }
}