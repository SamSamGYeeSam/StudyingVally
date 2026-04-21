package com.samsamgyeesam.studyingvally.domain.study.controller;

import com.samsamgyeesam.studyingvally.domain.study.dto.StudentEvaluationResponseDTO;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentCourse;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentEnrollment;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentCourseRepository;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentEnrollmentRepository;
import com.samsamgyeesam.studyingvally.domain.study.service.StudentCourseService;
import com.samsamgyeesam.studyingvally.domain.study.service.StudentSchoolService;
import com.samsamgyeesam.studyingvally.domain.study.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentSchoolController {

    private final StudentCourseRepository studentCourseRepository;
    private final StudentSchoolService studentSchoolService;
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final StudentCourseService studentCourseService;
    private final StudentService studentService;

    @GetMapping("/evaluations/{courseId}")
    @ResponseBody
    public List<StudentEvaluationResponseDTO> getEvaluations(@PathVariable("courseId") Long courseId) {
        return studentSchoolService.getCourseEvaluations(courseId);
    }

    @GetMapping("/school")
    public String schoolPage(Model model, Principal principal) {

        if (principal == null) {return "redirect:/auth/login";}

//        List<StudentEnrollment> enrollments = studentEnrollmentRepository.findAll();
//        List<StudentCourse> courses = enrollments.stream()
//                .map(StudentEnrollment::getCourse)
//                .distinct()
//                .collect(Collectors.toList());

        List<StudentCourse> courses = studentCourseService.getOpenCourses();

        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        List<Long> enrolledCourseIds = studentEnrollmentRepository.findByUserNo(userNo)
                .stream()
                .map(enrollment -> enrollment.getCourse().getCourseId())
                .collect(Collectors.toList());

        model.addAttribute("courses", courses);
        model.addAttribute("enrolledCourseIds", enrolledCourseIds);

        return "student/school";
    }

    @GetMapping("/school/list")
    public String list(Model model) {
        List<StudentCourse> courses = studentCourseService.getOpenCourses();
        model.addAttribute("courses", courses);
        return "student/school/list";
    }

    @PostMapping("/school/regist")
    public String registerCourse(@RequestParam("courseId") Long courseId,
                                 Principal principal,
                                 RedirectAttributes rttr) { // RedirectAttributes 추가

        if (principal == null) {
            rttr.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/auth/login";
        }

        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        studentSchoolService.registerCourse(userNo, courseId);

        rttr.addFlashAttribute("successMessage", "수강신청이 완료되었습니다!");
        return "redirect:/student/school";
    }

}
