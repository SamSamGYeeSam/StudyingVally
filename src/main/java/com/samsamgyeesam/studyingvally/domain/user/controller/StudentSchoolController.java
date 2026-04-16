package com.samsamgyeesam.studyingvally.domain.user.controller;

import com.samsamgyeesam.studyingvally.domain.course.dto.StudentEvaluationResponseDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.StudentCourse;
import com.samsamgyeesam.studyingvally.domain.course.entity.StudentEnrollment;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentCourseRepository;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentEnrollmentRepository;
import com.samsamgyeesam.studyingvally.domain.course.service.StudentCourseService;
import com.samsamgyeesam.studyingvally.domain.user.service.StudentSchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{courseId}")
    @ResponseBody
    public List<StudentEvaluationResponseDTO> getEvaluations(@PathVariable("courseId") Long courseId) {
        return studentSchoolService.getCourseEvaluations(courseId);
    }

    @GetMapping("/school")
    public String schoolPage(Model model) {
        // 핵심 해결: studentCourseRepository(Notice용) 대신 studentEnrollmentRepository 사용
        List<StudentEnrollment> enrollments = studentEnrollmentRepository.findAll();
        List<StudentCourse> courses = enrollments.stream()
                .map(StudentEnrollment::getCourse)
                .distinct()
                .collect(Collectors.toList());

        Long currentUserNo = 1L;
        List<Long> enrolledCourseIds = studentEnrollmentRepository.findByUserNo(currentUserNo)
                .stream()
                .map(enrollment -> enrollment.getCourse().getCourseId())
                .collect(Collectors.toList());

        model.addAttribute("courses", courses);
        model.addAttribute("enrolledCourseIds", enrolledCourseIds);

        return "student/school";
    }

    @GetMapping("/student/school/list")
    public String list(Model model) {
        List<StudentCourse> courses = studentCourseService.getOpenCourses();
        model.addAttribute("courses", courses);
        return "student/school/list";
    }

    @PostMapping("/school/regist")
    public String registerCourse(@RequestParam("courseId") Long courseId) {
        Long currentUserNo = 1L;

        studentSchoolService.registerCourse(currentUserNo, courseId);

        return "redirect:/student/school/list";
    }

}
