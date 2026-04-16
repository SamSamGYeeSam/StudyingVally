package com.samsamgyeesam.studyingvally.domain.course.controller;

import com.samsamgyeesam.studyingvally.domain.course.entity.StudentChapter;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentChapterAttemptRepository;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentChapterRepository;
import com.samsamgyeesam.studyingvally.domain.course.service.StudentCourseService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentCourseController {

    private final StudentCourseService  studentCourseService;
    private final StudentChapterRepository studentChapterRepository;
    private final StudentChapterAttemptRepository studentChapterAttemptRepository;

    @GetMapping("/course")
    public String enterCourse(@RequestParam("id") Long courseId, HttpSession session, Model model) {
//        Long userNo = (Long) session.getAttribute("userNo");
//        if (userNo == null) return "redirect:/main";

        Long userNo = 1L;

        double progress = studentCourseService.updateAndGetProgress(userNo, courseId);

        Map<String, Object> courseData = studentCourseService.getCourseRoomData(userNo, courseId);
        model.addAllAttributes(courseData);
        model.addAttribute("courseId", courseId);
        model.addAttribute("progress", progress);
        return "student/course";
    }

    @GetMapping("/chapter")
    public String getChapters(@RequestParam Long courseId, HttpSession session, Model model) {
        Long userNo = (Long) session.getAttribute("userNo");
        if (userNo == null) userNo = 1L; // 테스트용 임시 세션

        List<StudentChapter> chapters = studentChapterRepository.findByCourseId(courseId);

        List<Long> completedChapterIds = studentChapterAttemptRepository.findCompletedChapterNos(userNo, courseId);

        double progress = 0;
        if (!chapters.isEmpty()) {
            progress = (double) completedChapterIds.size() / chapters.size() * 100;
        }

        model.addAttribute("chapters", chapters);
        model.addAttribute("completedChapters", completedChapterIds); // 완료된 챕터 ID 리스트
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
    public ResponseEntity<String> completeChapter(@RequestParam Long courseId, @RequestParam Long chapNo) {
        Long userNo = 1L; // 임시
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
            HttpSession session)
    {
        Long userNo = (Long) session.getAttribute("userNo");
        if (userNo == null) userNo = 1L;

        studentCourseService.saveStudentEvaluation(userNo, courseId, rating, content);
        System.out.println("리뷰 저장됨: 강의=" + courseId + ", 별점=" + rating + ", 내용=" + content);

        return "redirect:/student/chapter?courseId=" + courseId;
    }

}
