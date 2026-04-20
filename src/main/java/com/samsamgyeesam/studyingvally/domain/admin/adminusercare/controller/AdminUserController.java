package com.samsamgyeesam.studyingvally.domain.admin.adminusercare.controller;

import com.samsamgyeesam.studyingvally.domain.admin.adminusercare.dto.AdminUserDetailResponseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.adminusercare.dto.AdminUserListResponseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.adminusercare.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 관리자 사용자 관리 컨트롤러
 *
 * 왜 필요한가:
 * - 사용자 목록 조회, 역할별 필터 조회, 상세 조회, 상태 변경 요청을 처리하기 위함이다.
 *
 * 리팩토링 포인트:
 * - 처리 후에는 redirect를 사용하여 PRG 패턴을 따른다.
 * - 상세 페이지는 GET 방식으로도 재조회 가능하게 구성한다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/usercare")
public class AdminUserController {

    /**
     * 사용자 관리 서비스
     */
    private final AdminUserService adminUserService;

    /**
     * 전체 사용자 목록 조회
     *
     * @param model 뷰 전달 객체
     * @return 사용자 관리 템플릿 경로
     */
    @GetMapping("")
    public String findAllUsers(Model model) {
        List<AdminUserListResponseDTO> userList = adminUserService.findAllUsers();

        model.addAttribute("userList", userList);
        model.addAttribute("selectedFilter", "ALL");

        return "admin/usercare";
    }

    /**
     * 선생님 목록 조회
     *
     * @param model 뷰 전달 객체
     * @return 사용자 관리 템플릿 경로
     */
    @GetMapping("/teacher")
    public String findAllTeachers(Model model) {
        List<AdminUserListResponseDTO> userList = adminUserService.findUsersByRole("TEACHER");

        model.addAttribute("userList", userList);
        model.addAttribute("selectedFilter", "TEACHER");

        return "admin/usercare";
    }

    /**
     * 학생 목록 조회
     *
     * @param model 뷰 전달 객체
     * @return 사용자 관리 템플릿 경로
     */
    @GetMapping("/student")
    public String findAllStudents(Model model) {
        List<AdminUserListResponseDTO> userList = adminUserService.findUsersByRole("STUDENT");

        model.addAttribute("userList", userList);
        model.addAttribute("selectedFilter", "STUDENT");

        return "admin/usercare";
    }

    /**
     * 사용자 상세 조회(GET)
     *
     * 왜 필요한가:
     * - 처리 후 redirect 대상이 되는 상세 페이지를 GET으로 열 수 있어야 한다.
     * - URL로 직접 접근해도 상세 조회가 가능해야 한다.
     *
     * @param userNo 사용자 번호
     * @param model 뷰 전달 객체
     * @return 사용자 상세 템플릿 경로
     */
    @GetMapping("/detail")
    public String findUserDetailGet(@RequestParam("userNo") Long userNo,
                                    Model model) {

        AdminUserDetailResponseDTO userDetail = adminUserService.findUserDetail(userNo);
        model.addAttribute("userDetail", userDetail);

        return "admin/userdetail";
    }

    /**
     * 사용자 상세 조회(POST)
     *
     * 왜 필요한가:
     * - 목록 화면에서 선택 버튼을 form submit으로 보내는 기존 구조를 유지하기 위함이다.
     *
     * @param userNo 사용자 번호
     * @param model 뷰 전달 객체
     * @return 사용자 상세 템플릿 경로
     */
    @PostMapping("/detail")
    public String findUserDetailPost(@RequestParam("userNo") Long userNo,
                                     Model model) {

        AdminUserDetailResponseDTO userDetail = adminUserService.findUserDetail(userNo);
        model.addAttribute("userDetail", userDetail);

        return "admin/userdetail";
    }

    /**
     * 사용자 활성화 처리
     *
     * 동작 원칙:
     * - 처리 후 상세 페이지로 redirect 한다.
     * - 새로고침 시 POST 재전송을 막기 위해 PRG 패턴을 따른다.
     *
     * @param userNo 사용자 번호
     * @return 리다이렉트 경로
     */
    @PostMapping("/enable")
    public String enableUser(@RequestParam("userNo") Long userNo) {
        adminUserService.enableUser(userNo);
        return "redirect:/admin/usercare/detail?userNo=" + userNo;
    }

    /**
     * 사용자 비활성화 처리
     *
     * @param userNo 사용자 번호
     * @return 리다이렉트 경로
     */
    @PostMapping("/disable")
    public String disableUser(@RequestParam("userNo") Long userNo) {
        adminUserService.disableUser(userNo);
        return "redirect:/admin/usercare/detail?userNo=" + userNo;
    }
}