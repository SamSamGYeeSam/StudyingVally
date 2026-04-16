package com.samsamgyeesam.studyingvally.domain.admin.controller;

import com.samsamgyeesam.studyingvally.domain.admin.service.AdminCallCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/callcenter")
@RequiredArgsConstructor
public class AdminCallCenterController {

    private final AdminCallCenterService adminCallCenterService;

    @GetMapping
    public String findAllCallCenterItems(Model model) {

        model.addAttribute("pageTitle", "고객센터 관리 페이지");
        model.addAttribute("menuList", adminCallCenterService.findAllCallCenterItems());

        return "admin/callcenter";
    }

    @GetMapping("/notice")
    public String findAllNoticesAdmin(Model model) {
        model.addAttribute("pageTitle", "공지사항 페이지");
        return "admin/noticelist";
    }

    @GetMapping("/contact")
    public String findAllContacts(Model model) {
        model.addAttribute("pageTitle", "문의함 페이지");
        return "admin/contactlist";
    }

    @GetMapping("/report")
    public String findAllReports(Model model) {
        model.addAttribute("pageTitle", "신고함 페이지");
        return "admin/reportlist";
    }
}