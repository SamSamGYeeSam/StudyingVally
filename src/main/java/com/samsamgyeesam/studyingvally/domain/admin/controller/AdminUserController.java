package com.samsamgyeesam.studyingvally.domain.admin.controller;

import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminUserListResponseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/* comment.
 *  관리자 사용자 관리 페이지 요청을 처리하는 컨트롤러
 *
 *  왜 필요한가?
 *  - 관리자 화면에서 사용자 목록 조회와 상태 변경 요청을 처리하기 위해 필요하다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {

    private final AdminUserService adminUserService;

    /* comment.
     *  사용자 관리 목록 페이지 반환 메서드
     */

    @GetMapping("/usercare")
    public String showUserCarePage(@RequestParam(value = "role", required = false) String role,
                                   Model model) {

        List<AdminUserListResponseDTO> userList;

        if (role == null || role.isBlank()) {
            userList = adminUserService.findAllUsers();
        } else {
            userList = adminUserService.findUsersByRole(role);
        }

        model.addAttribute("userList", userList);
        model.addAttribute("selectedRole", role);

        return "admin/usercare";
    }

    /* comment.
     *  사용자 계정 비활성화 처리 메서드
     */

    @PostMapping("/usercare/disable")
    public String disableUser(@RequestParam("userNo") Long userNo) {
        adminUserService.disableUser(userNo);
        return "redirect:/admin/usercare";
    }

    /* comment.
     *  사용자 계정 활성화 처리 메서드
     */

    @PostMapping("/usercare/enable")
    public String enableUser(@RequestParam("userNo") Long userNo) {
        adminUserService.enableUser(userNo);
        return "redirect:/admin/usercare";
    }
}