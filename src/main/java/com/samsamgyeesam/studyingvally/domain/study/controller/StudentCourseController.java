package com.samsamgyeesam.studyingvally.domain.study.controller;

import com.samsamgyeesam.studyingvally.domain.study.entity.StudentChapter;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentCourseQuestion;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentChapterAttemptRepository;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentChapterRepository;
import com.samsamgyeesam.studyingvally.domain.study.service.StudentCourseService;
import com.samsamgyeesam.studyingvally.domain.study.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentCourseController {

    private final StudentService studentService;
    private final StudentCourseService  studentCourseService;
    private final StudentChapterRepository studentChapterRepository;
    private final StudentChapterAttemptRepository studentChapterAttemptRepository;

//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//        if (request.getSession().getAttribute("userNo") == null) {
//            response.sendRedirect("/main");
//            return false;
//        }
//        return true;
//    }


    @Transactional
    @GetMapping("/course")
    public String enterCourse(@RequestParam("id") Long courseId, Principal principal, Model model) {

        if (principal == null) return "redirect:/auth/login";
        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);


        double progress = studentCourseService.updateAndGetProgress(userNo, courseId);

        Map<String, Object> courseData = studentCourseService.getCourseRoomData(userNo, courseId);
        model.addAllAttributes(courseData);
        model.addAttribute("courseId", courseId);
        model.addAttribute("progress", progress);
        return "student/course";
    }

    @GetMapping("/chapter")
    public String getChapters(@RequestParam Long courseId, Principal principal, Model model) {

        if (principal == null) return "redirect:/auth/login";
        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        List<StudentChapter> chapters = studentChapterRepository.findByCourseId(courseId);

        List<Long> completedChapterIds = studentChapterAttemptRepository.findCompletedChapterNos(userNo, courseId);

        double progress = 0;
        if (!chapters.isEmpty()) {
            progress = (double) completedChapterIds.size() / chapters.size() * 100;
        }

        model.addAttribute("chapters", chapters);
        model.addAttribute("completedChapters", completedChapterIds);
        model.addAttribute("progress", (int)progress);
        model.addAttribute("courseId", courseId);

        return "student/chapter";
    }

    @GetMapping("/chapter/class")
    public String watchChapter(@RequestParam("chapNo") Long chapNo,
                               @RequestParam("courseId") Long courseId,
                               Model model) {
        StudentChapter chapter = studentChapterRepository.findById(chapNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 없습니다."));

        model.addAttribute("chapter", chapter);
        model.addAttribute("courseId", courseId);
        return "student/chapterclass";
    }

    @PostMapping("/chapter/complete")
    @ResponseBody
    public ResponseEntity<String> completeChapter(@RequestParam Long courseId,
                                                  @RequestParam Long chapNo,
                                                  Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body("unauthorized");

        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        studentCourseService.completeChapter(userNo, chapNo, courseId);
        return ResponseEntity.ok("success");
    }

//    @GetMapping("/create/evaluation")
//    public String reviewForm(@RequestParam Long courseId, Model model) {
//        model.addAttribute("courseId", courseId);
//        return "evaluation";
//    }

    @PostMapping("/evaluation/save")
    public String saveStudentEvaluation(
            @RequestParam("courseId") Long courseId,
            @RequestParam("rating") int rating,
            @RequestParam("content") String content,
            Principal principal)
    {
        if (principal == null) return "redirect:/auth/login";
        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        studentCourseService.saveStudentEvaluation(userNo, courseId, rating, content);
        System.out.println("리뷰 저장됨: 강의=" + courseId + ", 별점=" + rating + ", 내용=" + content);

        return "redirect:/student/chapter?courseId=" + courseId;
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
        return "redirect:/student/course?id=" + courseId;
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
