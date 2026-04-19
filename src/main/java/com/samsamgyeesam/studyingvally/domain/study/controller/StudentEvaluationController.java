package com.samsamgyeesam.studyingvally.domain.study.controller;

import com.samsamgyeesam.studyingvally.domain.study.dto.StudentEvaluationResponseDTO;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentEvaluation;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentEvaluationRepository;
import com.samsamgyeesam.studyingvally.domain.study.service.StudentEvaluationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/student/evaluation")
@RequiredArgsConstructor
public class StudentEvaluationController {

    private final StudentEvaluationService studentEvaluationService;

    @GetMapping("/{courseId}")
    @ResponseBody
    public List<StudentEvaluationResponseDTO> getEvaluation(@PathVariable Long courseId) {
        return studentEvaluationService.getEvaluationsByCourseId(courseId);
    }

    @PostMapping("/write")
    public String evaluationForm(jakarta.servlet.http.HttpSession session, Model model) {
        Long courseId = (Long) session.getAttribute("currentCourseId");
        if (courseId == null) {
            return "redirect:/student/home";
        }
        model.addAttribute("courseId", courseId);
        return "student/evaluation";
    }

    @PostMapping("/submit")
    public String saveEvaluation(@RequestParam("courseId") Long courseId,
                                 @RequestParam("rating") Double rating,
                                 @RequestParam("content") String content,
                                 HttpSession session) {
        Long userNo = (Long) session.getAttribute("userNo");

        return "redirect:/student/home";
    }

    @PostMapping("/detail")
    public String viewMyEvaluation(HttpSession session, Model model) {
        Long courseId = (Long) session.getAttribute("currentCourseId");
        return "student/evaluation";
    }

    @Autowired
    private StudentEvaluationRepository studentEvaluationRepository;

    @GetMapping("/api/detail")
    @ResponseBody
    public ResponseEntity<?> getEvaluationApi(@RequestParam("courseId") Long courseId, HttpSession session) {
        Long userNo = (Long) session.getAttribute("userNo");
        if (userNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Optional<StudentEvaluation> evalOpt = studentEvaluationRepository
                .findByUser_UserNoAndStudentCourse_CourseId(userNo, courseId);

        if (evalOpt.isPresent()) {
            StudentEvaluation eval = evalOpt.get();
            Map<String, Object> result = new HashMap<>();
            result.put("rating", eval.getEvaluationScore());
            result.put("content", eval.getEvaluationDesc());
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("작성된 수강평이 없습니다.");
        }
    }

}