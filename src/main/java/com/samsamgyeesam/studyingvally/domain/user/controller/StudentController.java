package com.samsamgyeesam.studyingvally.domain.user.controller;

import com.samsamgyeesam.studyingvally.domain.admin.dto.StudentAdminNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.course.dto.StudentCourseNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.course.service.StudentCourseService;
import com.samsamgyeesam.studyingvally.domain.user.dto.StudentDTO;
import com.samsamgyeesam.studyingvally.domain.user.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StudentCourseService studentCourseService;

    @GetMapping("/main")
    public String studentMain(Model model) {
        try {
            StudentDTO studentDTO = studentService.getStudentMainData(6L);
            model.addAttribute("student", studentDTO);
        } catch (IllegalAccessException e) {
        } catch (Exception e) {
            model.addAttribute("errorMessage", "데이터를 불러오는 중 문제가 발생했습니다.");
            return "error/500";
        }
        return "student/main";
    }

    @GetMapping("/home")
    public String studentHome(Model model) throws IllegalAccessException {
        Long userNo = 6L;
        StudentDTO student = studentService.getStudentMainData(userNo);
        model.addAttribute("student", student);
        List<Map<String, Object>> courseList = studentCourseService.getStudentCourseStatus(userNo);
        model.addAttribute("courseList", courseList);
        return "student/home";
    }

//    @GetMapping("/school")
//    public String findCourseList(Model model) {
//        return "student/school";
//    }

//    @PostMapping("/school/regist")
//    public String registCourse(@RequestParam("courseNo") Long courseNo) {
//        return "redirect:/student/home/course";
//    }

    @GetMapping("/quiz")
    public String studentQuiz() {
        return "student/quiz";
    }

//    @GetMapping("/notice")
//    public String studentNotice() {
//        return "student/notice";
//    }

    @GetMapping("/home/courses")
    public String studentFindDetail(Model model) throws IllegalAccessException {
        StudentDTO student = studentService.getStudentMainData(1L);
        model.addAttribute("student", student);
        return "student/home-courses";
    }

    @GetMapping("/notice")
    public String studentNotice(Model model) throws IllegalAccessException {
        Long userNo = 6L;

        List<StudentAdminNoticeDTO> adminNotices = studentService.getAdminNotices();
        List<StudentCourseNoticeDTO> courseNotices = studentCourseService.getCourseNoticesForStudent(userNo);

        model.addAttribute("adminNotices", adminNotices);
        model.addAttribute("courseNotices", courseNotices);

        return "student/notice";

//    @GetMapping("/course")
//    public String findCourse() {
//        return "student/course";
//    }

    }
}
