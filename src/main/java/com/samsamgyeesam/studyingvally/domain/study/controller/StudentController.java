package com.samsamgyeesam.studyingvally.domain.study.controller;

import com.samsamgyeesam.studyingvally.domain.study.dto.StudentAdminNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.study.dto.StudentCourseNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentEnrollment;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentEnrollmentRepository;
import com.samsamgyeesam.studyingvally.domain.study.service.StudentCourseService;
import com.samsamgyeesam.studyingvally.domain.study.dto.StudentDTO;
import com.samsamgyeesam.studyingvally.domain.study.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StudentCourseService studentCourseService;
    private final StudentEnrollmentRepository studentEnrollmentRepository;

    @Transactional(readOnly = true)
    @GetMapping("/main")
    public String studentMain(Model model, Principal principal) {
        try {
            if (principal == null) return "redirect:/auth/login";

            String userId = principal.getName();
            Long userNo = studentService.findUserNoByUserId(userId);

            StudentDTO studentDTO = studentService.getStudentMainData(userNo);
            model.addAttribute("student", studentDTO);

            List<StudentEnrollment> enrollments = studentEnrollmentRepository.findByUserNo(userNo);
            model.addAttribute("enrollments", enrollments);

        } catch (Exception e) {
            e.printStackTrace();
            return "error/500";
        }
        return "student/main";
    }

    @GetMapping("/home")
    public String studentHome(Model model, Principal principal) throws IllegalAccessException {

        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        StudentDTO student = studentService.getStudentMainData(userNo);

        List<StudentDTO.EnrolledCourseDTO> activeCourses = student.getEnrolledCourses().stream()
                .filter(course -> "OPEN".equals(course.getCourseStatus()))
                .collect(Collectors.toList());

        model.addAttribute("student", student);
        model.addAttribute("courseList", student.getEnrolledCourses());
        return "student/home";
    }


    @GetMapping("/home/courses")
    public String studentFindDetail(Model model, Principal principal) throws IllegalAccessException {

        if (principal == null) return "redirect:/auth/login";
        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        StudentDTO student = studentService.getStudentMainData(userNo);
        model.addAttribute("student", student);
        return "student/home-courses";
    }

    @GetMapping("/notice")
    public String studentNotice(Model model, Principal principal) throws IllegalAccessException {
        String userId = principal.getName();
        Long userNo = studentService.findUserNoByUserId(userId);

        List<StudentAdminNoticeDTO> adminNotices = studentService.getAdminNotices();
        List<StudentCourseNoticeDTO> courseNotices = studentCourseService.getCourseNoticesForStudent(userNo);

        model.addAttribute("adminNotices", adminNotices);
        model.addAttribute("courseNotices", courseNotices);

        return "student/notice";


    }
}