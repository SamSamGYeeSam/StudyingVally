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
    public String callCenterMainPage(Model model) {

        model.addAttribute("pageTitle", "고객센터 관리 페이지");

        return "admin/callcenter";
    }

    @GetMapping("/notice")
    public String findAllNoticeList(Model model) {

        model.addAttribute("pageTitle", "공지사항 목록");
        model.addAttribute("noticeList", adminCallCenterService.findAllNoticeList());

        return "admin/noticelist";
    }

    @GetMapping("/contact")
    public String findAllContactList(Model model) {

        model.addAttribute("pageTitle", "문의함 목록");
        model.addAttribute("contactList", adminCallCenterService.findAllContactList());

        return "admin/contactlist";
    }

    @GetMapping("/report")
    public String findAllReportList(Model model) {

        model.addAttribute("pageTitle", "신고함 목록");
        model.addAttribute("reportList", adminCallCenterService.findAllReportList());

        return "admin/reportlist";
    }

    @GetMapping("/noticeregist")
    public String noticeRegistPage(Model model) {

        model.addAttribute("pageTitle", "공지사항 추가 페이지");

        return "admin/noticeregist";
    }
}