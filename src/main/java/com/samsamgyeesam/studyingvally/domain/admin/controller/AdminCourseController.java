package com.samsamgyeesam.studyingvally.domain.admin.controller;

import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminCourseDetailResponseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminManagedCourseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.service.AdminCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/* comment.
 * 관리자 강의 관리 페이지 요청을 처리하는 컨트롤러
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/coursecare")
public class AdminCourseController {

    private final AdminCourseService adminCourseService;

    /* comment.
     * 개설 요청된 전체 강의 목록 조회
     */
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

    /* comment.
     * 강의 상세 조회
     * displayNo는 목록 화면의 순번을 상세 화면에 그대로 전달하기 위한 값
     */
    @PostMapping("/detail")
    public String findCourseDetail(@RequestParam("courseId") Long courseId,
                                   @RequestParam("displayNo") Long displayNo,
                                   Model model) {

        AdminCourseDetailResponseDTO courseDetail = adminCourseService.findCourseDetail(courseId);

        model.addAttribute("courseDetail", courseDetail);
        model.addAttribute("displayNo", displayNo);

        return "admin/coursedetail";
    }

    /* comment.
     * 강의 활성화 처리
     * 상세 페이지에서 다시 돌아올 때 번호 유지
     */
    @PostMapping("/open")
    public String openCourse(@RequestParam("courseId") Long courseId,
                             @RequestParam("displayNo") Long displayNo,
                             Model model) {

        adminCourseService.openCourse(courseId);

        AdminCourseDetailResponseDTO courseDetail = adminCourseService.findCourseDetail(courseId);

        model.addAttribute("courseDetail", courseDetail);
        model.addAttribute("displayNo", displayNo);

        return "admin/coursedetail";
    }

    /* comment.
     * 강의 비활성화 처리
     * 상세 페이지에서 다시 돌아올 때 번호 유지
     */
    @PostMapping("/close")
    public String closeCourse(@RequestParam("courseId") Long courseId,
                              @RequestParam("displayNo") Long displayNo,
                              Model model) {

        adminCourseService.closeCourse(courseId);

        AdminCourseDetailResponseDTO courseDetail = adminCourseService.findCourseDetail(courseId);

        model.addAttribute("courseDetail", courseDetail);
        model.addAttribute("displayNo", displayNo);

        return "admin/coursedetail";
    }
}