package com.samsamgyeesam.studyingvally.domain.study.controller;

import com.samsamgyeesam.studyingvally.domain.study.entity.StudentChapter;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentCourseQuestion;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentChapterAttemptRepository;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentChapterRepository;
import com.samsamgyeesam.studyingvally.domain.study.service.StudentCourseService;
import com.samsamgyeesam.studyingvally.domain.study.service.StudentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

import static javax.swing.text.html.CSS.getAttribute;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentCourseController {

    private final StudentService studentService;
    private final StudentCourseService  studentCourseService;
    private final StudentChapterRepository studentChapterRepository;
    private final StudentChapterAttemptRepository studentChapterAttemptRepository;

    @PostMapping("/course/enter")
    public String enterCourse(@RequestParam("courseId") Long courseId, HttpSession session) {
        session.setAttribute("currentCourseId", courseId);
        return "redirect:/student/course";
    }

    @Transactional
    @GetMapping("/course")
    public String studentCourseView(Principal principal, HttpSession session, Model model) {
        if (principal == null) return "redirect:/auth/login";

        Long courseId = (Long) session.getAttribute("currentCourseId");

        if (courseId == null) {
            return "redirect:/student/home";
        }

        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        double progress = studentCourseService.updateAndGetProgress(userNo, courseId);
        Map<String, Object> courseData = studentCourseService.getCourseRoomData(userNo, courseId);

        model.addAllAttributes(courseData);
        model.addAttribute("courseId", courseId);
        model.addAttribute("progress", progress);

        return "student/course";
    }

    @PostMapping("/chapter")
    public String handleChapterEntry(HttpSession session) {
        Long courseId = (Long) session.getAttribute("currentCourseId");
        if (courseId == null) return "redirect:/student/home";
        return "redirect:/student/chapter";
    }

    @GetMapping("/chapter")
    public String getChapters(Principal principal, HttpSession session, Model model) {
        if (principal == null) return "redirect:/auth/login";

        Long courseId = (Long) session.getAttribute("currentCourseId");
        if (courseId == null) return "redirect:/student/home";

        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        List<StudentChapter> chapters = studentChapterRepository.findByCourseId(courseId);
        List<Long> completedChapterIds = studentChapterAttemptRepository.findCompletedChapterNos(userNo, courseId);

        double progress = chapters.isEmpty() ? 0 : (double) completedChapterIds.size() / chapters.size() * 100;

        model.addAttribute("chapters", chapters);
        model.addAttribute("completedChapters", completedChapterIds);
        model.addAttribute("progress", (int)progress);
        model.addAttribute("courseId", courseId); // 기존 변수명 유지

        return "student/chapter";
    }

    @PostMapping("/chapter/class")
    public String handleWatchEntry(@RequestParam("chapNo") Long chapNo, HttpSession session) {
        session.setAttribute("currentChapNo", chapNo);
        return "redirect:/student/chapter/class";
    }

    @GetMapping("/chapter/class")
    public String watchChapter(HttpSession session, Model model) {
        Long courseId = (Long) session.getAttribute("currentCourseId");
        Long chapNo = (Long) session.getAttribute("currentChapNo");

        if (courseId == null || chapNo == null) return "redirect:/student/chapter";

        StudentChapter chapter = studentChapterRepository.findById(chapNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 없습니다."));

        model.addAttribute("chapter", chapter);
        model.addAttribute("courseId", courseId); // 기존 변수명 유지
        return "student/chapterclass";
    }

    @PostMapping("/chapter/complete/{courseId}/{chapNo}")
    @ResponseBody
    public ResponseEntity<String> completeChapter(@PathVariable Long courseId,
                                                  @PathVariable Long chapNo,
                                                  Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body("unauthorized");

        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        studentCourseService.completeChapter(userNo, chapNo, courseId);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/evaluation/save")
    public String saveStudentEvaluation(
            @RequestParam("courseId") Long courseId,
            @RequestParam("rating") int rating,
            @RequestParam("content") String content,
            Principal principal, HttpServletRequest request)
    {
        if (principal == null) return "redirect:/auth/login";
        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        studentCourseService.saveStudentEvaluation(userNo, courseId, rating, content);
        System.out.println("리뷰 저장됨: 강의=" + courseId + ", 별점=" + rating + ", 내용=" + content);

        return "redirect:/student/course";
//        String referer = request.getHeader("Referer");
//
//        if (referer != null && !referer.isEmpty()) {
//            return "redirect:/student/chapter/class";
//        }
//
//        return "redirect:/student/home";
    }

    @GetMapping("/course/talk")
    public String questionForm(@RequestParam Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "student/questionform";
    }

    @PostMapping("/course/question/save")
    public String saveQuestion(@RequestParam("courseId") Long courseId,
                               @RequestParam("title") String title,
                               @RequestParam("desc") String desc,
                               Principal principal) {
        if (principal == null) return "redirect:/auth/login";
        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        studentCourseService.saveQuestion(userNo, courseId, title, desc);
        return "redirect:/student/home";
    }

    @GetMapping("/course/mailbox")
    public String mailbox(Long courseId, Principal principal, Model model) {
        if (principal == null) return "redirect:/auth/login";
        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        List<StudentCourseQuestion> myQuestions = studentCourseService.getQuestionsByCourse(userNo, courseId);
        model.addAttribute("questions", myQuestions);
        model.addAttribute("courseId", courseId);
        return "student/mailbox"; // 우체통 HTML
    }

    @GetMapping("/course/my-questions")
    @ResponseBody
    public List<StudentCourseQuestion> getMyQuestionsJson(@RequestParam("courseId") Long courseId, Principal principal) {
        if (principal == null) return null;

        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        return studentCourseService.getQuestionsByCourse(userNo, courseId);
    }

}
