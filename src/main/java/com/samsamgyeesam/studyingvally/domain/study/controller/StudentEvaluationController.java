package com.samsamgyeesam.studyingvally.domain.study.controller;

import com.samsamgyeesam.studyingvally.domain.study.dto.StudentEvaluationResponseDTO;
import com.samsamgyeesam.studyingvally.domain.study.service.StudentEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return "redirect:/student/main";
        }
        model.addAttribute("courseId", courseId);
        return "student/evaluation"; 
    }
}