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

    @GetMapping("/write")
    public String evaluationForm(@RequestParam("courseId") Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "student/evaluation"; // templates/evaluation.html
    }
}