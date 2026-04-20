package com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.controller;

import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.dto.AdminCourseDetailResponseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.dto.AdminManagedCourseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.service.AdminCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/coursecare")
public class AdminCourseController {

    private final AdminCourseService adminCourseService;

    @GetMapping("")
    public String findAllCourseAdmin(@RequestParam(value = "status", required = false) String status,
                                     Model model) {

        List<AdminManagedCourseDTO> courseList;

        if (status == null || status.isBlank()) {
            courseList = adminCourseService.findAllCourses();
            model.addAttribute("selectedStatus", "ALL");
        } else {
            courseList = adminCourseService.findCoursesByStatus(status);
            model.addAttribute("selectedStatus", status);
        }

        model.addAttribute("courseList", courseList);

        return "admin/coursecare";
    }

    @GetMapping("/detail")
    public String findCourseDetailGet(@RequestParam("courseId") Long courseId, Model model) {
        AdminCourseDetailResponseDTO courseDetail = adminCourseService.findCourseDetail(courseId);
        model.addAttribute("courseDetail", courseDetail);
        return "admin/coursedetail";
    }

    @PostMapping("/detail")
    public String findCourseDetailPost(@RequestParam("courseId") Long courseId) {
        return "redirect:/admin/coursecare/detail?courseId=" + courseId;
    }

    @PostMapping("/open")
    public String openCourse(@RequestParam("courseId") Long courseId) {
        adminCourseService.openCourse(courseId);
        return "redirect:/admin/coursecare/detail?courseId=" + courseId;
    }

    @PostMapping("/close")
    public String closeCourse(@RequestParam("courseId") Long courseId) {
        adminCourseService.closeCourse(courseId);
        return "redirect:/admin/coursecare/detail?courseId=" + courseId;
    }
}