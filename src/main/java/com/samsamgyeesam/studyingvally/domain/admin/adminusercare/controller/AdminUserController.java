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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/usercare")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping("")
    public String findAllUsers(Model model) {
        List<AdminUserListResponseDTO> userList = adminUserService.findAllUsers();

        model.addAttribute("userList", userList);
        model.addAttribute("selectedFilter", "ALL");

        return "admin/usercare";
    }

    @GetMapping("/teacher")
    public String findAllTeachers(Model model) {
        List<AdminUserListResponseDTO> userList = adminUserService.findUsersByRole("TEACHER");

        model.addAttribute("userList", userList);
        model.addAttribute("selectedFilter", "TEACHER");

        return "admin/usercare";
    }

    @GetMapping("/student")
    public String findAllStudents(Model model) {
        List<AdminUserListResponseDTO> userList = adminUserService.findUsersByRole("STUDENT");

        model.addAttribute("userList", userList);
        model.addAttribute("selectedFilter", "STUDENT");

        return "admin/usercare";
    }

    @GetMapping("/detail")
    public String findUserDetailGet(@RequestParam("userNo") Long userNo, Model model) {
        AdminUserDetailResponseDTO userDetail = adminUserService.findUserDetail(userNo);
        model.addAttribute("userDetail", userDetail);
        return "admin/userdetail";
    }

    @PostMapping("/detail")
    public String findUserDetailPost(@RequestParam("userNo") Long userNo) {
        return "redirect:/admin/usercare/detail?userNo=" + userNo;
    }

    @PostMapping("/enable")
    public String enableUser(@RequestParam("userNo") Long userNo, RedirectAttributes redirectAttributes) {
        adminUserService.enableUser(userNo);
        redirectAttributes.addFlashAttribute("successMessage", "사용자 계정이 활성화 되었습니다.");
        return "redirect:/admin/usercare/detail?userNo=" + userNo;
    }

    @PostMapping("/disable")
    public String disableUser(@RequestParam("userNo") Long userNo, RedirectAttributes redirectAttributes) {
        adminUserService.disableUser(userNo);
        redirectAttributes.addFlashAttribute("successMessage", "사용자 계정이 비활성화 되었습니다..");
        return "redirect:/admin/usercare/detail?userNo=" + userNo;
    }
}