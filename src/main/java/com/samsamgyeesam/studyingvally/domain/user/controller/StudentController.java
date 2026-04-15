package com.samsamgyeesam.studyingvally.domain.user.controller;

import com.samsamgyeesam.studyingvally.domain.user.dto.StudentDTO;
import com.samsamgyeesam.studyingvally.domain.user.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/main")
    public String studentMain(Model model) {
        try {
            StudentDTO studentDTO = studentService.getStudentMainData(1L);
            model.addAttribute("student", studentDTO);        } catch (IllegalAccessException e) {
        } catch (Exception e) {
            model.addAttribute("errorMessage", "데이터를 불러오는 중 문제가 발생했습니다.");
            return "error/500";
        }
        return "student/main";
    }

    @GetMapping("/home")
    public String studentHome(Model model) throws IllegalAccessException {
        StudentDTO student = studentService.getStudentMainData(1L);
        model.addAttribute("student", student);
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

    @GetMapping("/notice")
    public String studentNotice() {
        return "student/notice";
    }

    @GetMapping("/home/course")
    public String studentFindDetail(Model model) throws IllegalAccessException {
        StudentDTO student = studentService.getStudentMainData(1L);
        model.addAttribute("student", student);
        return "student/home-course";
    }

    @GetMapping("/course")
    public String findCourse() {
        return "student/course";
    }

}
