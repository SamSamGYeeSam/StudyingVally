package com.samsamgyeesam.studyingvally.domain.study.controller;

import com.samsamgyeesam.studyingvally.domain.study.dto.StudentDTO;
import com.samsamgyeesam.studyingvally.domain.study.dto.StudentEvaluationResponseDTO;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentEvaluation;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentEvaluationRepository;
import com.samsamgyeesam.studyingvally.domain.study.service.StudentEvaluationService;
import com.samsamgyeesam.studyingvally.domain.study.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/student/evaluation")
@RequiredArgsConstructor
public class StudentEvaluationController {

    private final StudentEvaluationService studentEvaluationService;
    private final StudentService studentService;


    @GetMapping("/{courseId}")
    @ResponseBody
    public List<StudentEvaluationResponseDTO> getEvaluation(@PathVariable Long courseId) {
        return studentEvaluationService.getEvaluationsByCourseId(courseId);
    }

    @PostMapping("/write")
    public String evaluationForm(HttpSession session,
                                 Principal principal,
                                 Model model,
                                 RedirectAttributes rttr) {

        Long courseId = (Long) session.getAttribute("currentCourseId");
        if (courseId == null) return "redirect:/student/home";

        Long userNo = studentService.findUserNoByUserId(principal.getName());

        if (studentEvaluationService.getProgress(userNo, courseId) < 100) {
            rttr.addFlashAttribute("errorMessage", "챕터를 다 들으면 강의평을 쓸 수 있어! 💪");
            rttr.addAttribute("courseId", courseId);
            return "redirect:/student/course";
        }

        model.addAttribute("courseId", courseId);
        return "student/evaluation";
    }


    @PostMapping("/save")
    public String saveStudentEvaluation(
            @RequestParam("courseId") Long courseId,
            @RequestParam("rating") int rating,
            @RequestParam("content") String content,
            Principal principal, RedirectAttributes rttr) {
        if (principal == null) return "redirect:/auth/login";
        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        studentEvaluationService.saveStudentEvaluation(userNo, courseId, rating, content);

        rttr.addFlashAttribute("successMessage", "강의평 작성 완료! 네 열정에 박수를 보낼게! 👏");
        rttr.addAttribute("courseId", courseId);

        return "redirect:/student/course";
    }

    @PostMapping("/detail")
    public String viewMyEvaluation() {
        return "student/course";
    }

    @Autowired
    private StudentEvaluationRepository studentEvaluationRepository;

    @GetMapping("/api/detail")
    @ResponseBody
    public ResponseEntity<?> getEvaluationApi(@RequestParam("courseId") Long courseId, java.security.Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

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