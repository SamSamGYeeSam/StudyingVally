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

/* comment.
 * 관리자 사용자 관리 페이지 요청을 처리하는 컨트롤러
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/usercare")
public class AdminUserController {

    private final AdminUserService adminUserService;

    /* comment.
     * 전체 사용자 목록 조회
     */
    @GetMapping("")
    public String findAllUsers(Model model) {

        List<AdminUserListResponseDTO> userList = adminUserService.findAllUsers();

        model.addAttribute("userList", userList);
        model.addAttribute("selectedFilter", "ALL");

        return "admin/usercare";
    }

    /* comment.
     * 선생님 사용자 목록 조회
     */
    @GetMapping("/teacher")
    public String findAllTeachers(Model model) {

        List<AdminUserListResponseDTO> userList = adminUserService.findUsersByRole("TEACHER");

        model.addAttribute("userList", userList);
        model.addAttribute("selectedFilter", "TEACHER");

        return "admin/usercare";
    }

    /* comment.
     * 학생 사용자 목록 조회
     */
    @GetMapping("/student")
    public String findAllStudents(Model model) {

        List<AdminUserListResponseDTO> userList = adminUserService.findUsersByRole("STUDENT");

        model.addAttribute("userList", userList);
        model.addAttribute("selectedFilter", "STUDENT");

        return "admin/usercare";
    }

    /* comment.
     * 사용자 상세 조회
     * userNo는 form body로 전달받아 주소창에 노출되지 않도록 처리
     */
    @PostMapping("/detail")
    public String findUserDetail(@RequestParam("userNo") Long userNo,
                                 Model model) {

        AdminUserDetailResponseDTO userDetail = adminUserService.findUserDetail(userNo);

        model.addAttribute("userDetail", userDetail);

        return "admin/userdetail";
    }

    /* comment.
     * 사용자 활성화 처리
     */
    @PostMapping("/enable")
    public String enableUser(@RequestParam("userNo") Long userNo,
                             Model model) {

        adminUserService.enableUser(userNo);

        AdminUserDetailResponseDTO userDetail = adminUserService.findUserDetail(userNo);
        model.addAttribute("userDetail", userDetail);

        return "admin/userdetail";
    }

    /* comment.
     * 사용자 비활성화 처리
     */
    @PostMapping("/disable")
    public String disableUser(@RequestParam("userNo") Long userNo,
                              Model model) {

        adminUserService.disableUser(userNo);

        AdminUserDetailResponseDTO userDetail = adminUserService.findUserDetail(userNo);
        model.addAttribute("userDetail", userDetail);

        return "admin/userdetail";
    }
}