package com.samsamgyeesam.studyingvally.domain.quiz.controller;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizChapterDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizEnrolledCourseDTO;
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
// 챕터 번호(chapNo)도 세션에 유지하도록 추가
@SessionAttributes({"courseId", "chapNo"})
public class QuizStudentController {

    private final QuizStudentService quizStudentService;
    private final QuizService quizService; // 강사용에서 만든 서비스를 재사용하여 챕터/퀴즈 조회!

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
        return "redirect:/student/quiz/select"; // 강의 선택 후 챕터 목록으로 이동
    }

    // ==========================================
    // 3. 선택한 강의의 챕터 목록 출력
    // ==========================================
    @GetMapping("/student/quiz/select")
    public String findQuizSelect(@ModelAttribute("courseId") Long courseId, Model model) {
        // 이미 만들어둔 QuizService를 활용하여 챕터 목록 조회
        List<QuizChapterDTO> chapterList = quizService.getQuizChapterListByCourseId(courseId);
        model.addAttribute("chapterList", chapterList);
        return "quiz/student-quiz/student_chapter_list";
    }

    @PostMapping("/student/quiz/chapter-post")
    public String selectChapterPost(@RequestParam("chapNo") Long chapNo, Model model) {
        model.addAttribute("chapNo", chapNo); // 챕터 번호 세션 저장
        return "redirect:/student/quiz/list"; // 챕터 선택 후 퀴즈 목록으로 이동
    }

    // ==========================================
    // 4. 선택한 챕터의 퀴즈 목록 출력
    // ==========================================
    @GetMapping("/student/quiz/list")
    public String findQuizList(@ModelAttribute("chapNo") Long chapNo, Model model) {
        // QuizService를 활용하여 퀴즈 목록 조회
        List<QuizDTO> quizList = quizService.getQuizListByChapNo(chapNo);
        model.addAttribute("quizList", quizList);
        return "quiz/student-quiz/student_quiz_list";
    }
}