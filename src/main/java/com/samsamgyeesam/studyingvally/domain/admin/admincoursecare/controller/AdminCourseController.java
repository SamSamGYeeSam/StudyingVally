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

/**
 * 관리자 강의 관리 컨트롤러
 *
 * 왜 필요한가:
 * - 강의 목록 조회, 상세 조회, 활성화/비활성화 요청을 처리하기 위함이다.
 *
 * 리팩토링 포인트:
 * - 상세 조회 기준을 courseId 하나로 통일한다.
 * - 화면 표시용 번호(displayNo)는 사용하지 않는다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/coursecare")
public class AdminCourseController {

    /**
     * 강의 관리 서비스
     */
    private final AdminCourseService adminCourseService;

    /**
     * 전체 또는 상태별 강의 목록 조회
     *
     * @param status 상태 필터 값
     * @param model 뷰 전달 객체
     * @return 강의 관리 템플릿 경로
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

    /**
     * 강의 상세 조회(GET)
     *
     * @param courseId 강의 번호
     * @param model 뷰 전달 객체
     * @return 강의 상세 템플릿 경로
     */
    @GetMapping("/detail")
    public String findCourseDetailGet(@RequestParam("courseId") Long courseId,
                                      Model model) {

        AdminCourseDetailResponseDTO courseDetail = adminCourseService.findCourseDetail(courseId);
        model.addAttribute("courseDetail", courseDetail);

        return "admin/coursedetail";
    }

    /**
     * 강의 상세 조회(POST)
     *
     * 왜 필요한가:
     * - 목록 화면의 선택 버튼이 form submit 구조이므로 기존 흐름을 유지하기 위함이다.
     *
     * @param courseId 강의 번호
     * @param model 뷰 전달 객체
     * @return 강의 상세 템플릿 경로
     */
    @PostMapping("/detail")
    public String findCourseDetailPost(@RequestParam("courseId") Long courseId,
                                       Model model) {

        AdminCourseDetailResponseDTO courseDetail = adminCourseService.findCourseDetail(courseId);
        model.addAttribute("courseDetail", courseDetail);

        return "admin/coursedetail";
    }

    /**
     * 강의 활성화 처리
     *
     * @param courseId 강의 번호
     * @return 리다이렉트 경로
     */
    @PostMapping("/open")
    public String openCourse(@RequestParam("courseId") Long courseId) {
        adminCourseService.openCourse(courseId);
        return "redirect:/admin/coursecare/detail?courseId=" + courseId;
    }

    /**
     * 강의 비활성화 처리
     *
     * @param courseId 강의 번호
     * @return 리다이렉트 경로
     */
    @PostMapping("/close")
    public String closeCourse(@RequestParam("courseId") Long courseId) {
        adminCourseService.closeCourse(courseId);
        return "redirect:/admin/coursecare/detail?courseId=" + courseId;
    }
}