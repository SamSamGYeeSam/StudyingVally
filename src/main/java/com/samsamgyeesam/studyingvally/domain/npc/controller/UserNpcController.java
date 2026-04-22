package com.samsamgyeesam.studyingvally.domain.npc.controller;

import com.samsamgyeesam.studyingvally.domain.npc.dto.UserNpcQuestionTechDTO;
import com.samsamgyeesam.studyingvally.domain.npc.dto.UserReportDTO;
import com.samsamgyeesam.studyingvally.domain.npc.exception.NpcException;
import com.samsamgyeesam.studyingvally.domain.npc.service.UserNpcService;
import com.samsamgyeesam.studyingvally.domain.npc.service.UserReportService;
import com.samsamgyeesam.studyingvally.domain.user.service.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/npc")
public class UserNpcController {

    private final UserNpcService userNpcService;
    private final UserReportService userReportService; // 신고 서비스 추가

    // ==================== [메인 화면] ====================
    @GetMapping
    public String npcMain(@AuthenticationPrincipal AuthUserDetails userDetails, Model model) {
        // 1. 엔티티 필드명에 맞춰 getUserRole() 사용 (문자열이 null일 경우를 대비해 빈 문자열 처리)
        String roleStr = userDetails.getRole() != null ? userDetails.getRole() : "";

        // 2. 대소문자 구분이나 "ROLE_" 접두사 유무에 상관없이 안전하게 "TEACHER" 판별
        if (roleStr.toUpperCase().contains("TEACHER")) {
            model.addAttribute("role", "TEACHER");
        } else {
            model.addAttribute("role", "STUDENT");
        }

        return "npc/main";
    }

    // ==================== [1:1 문의 (Inquiry)] ====================
    @GetMapping("/inquiry")
    public String inquiryForm() {
        return "npc/inquiry_form";
    }

    @PostMapping("/inquiry")
    public String submitInquiry(@ModelAttribute UserNpcQuestionTechDTO dto,
                                @AuthenticationPrincipal AuthUserDetails userDetails,
                                RedirectAttributes rttr) {
        try {
            userNpcService.registInquiry(dto, userDetails.getUserNo());
            rttr.addFlashAttribute("successMessage", "문의가 성공적으로 접수되었습니다.");
            return "redirect:/npc";
        } catch (NpcException e) {
            rttr.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/npc/inquiry";
        }
    }

    @GetMapping("/my-inquiries")
    public String myInquiries(@AuthenticationPrincipal AuthUserDetails userDetails, Model model) {
        List<UserNpcQuestionTechDTO> inquiries = userNpcService.getMyInquiries(userDetails.getUserNo());
        model.addAttribute("inquiries", inquiries);
        return "npc/my_inquiries";
    }

    // ==================== [신고하기 (Report)] ====================
    @GetMapping("/report")
    public String reportForm() {
        return "npc/report_form";
    }

    @PostMapping("/report")
    public String submitReport(@ModelAttribute UserReportDTO dto,
                               @AuthenticationPrincipal AuthUserDetails userDetails,
                               RedirectAttributes rttr) {
        try {
            userReportService.registReport(dto, userDetails.getUserNo());
            rttr.addFlashAttribute("successMessage", "신고가 정상적으로 접수되었습니다.");
            return "redirect:/npc";
        } catch (NpcException e) {
            rttr.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/npc/report";
        }
    }

    @GetMapping("/report/my-reports")
    public String myReports(@AuthenticationPrincipal AuthUserDetails userDetails, Model model) {
        List<UserReportDTO> reports = userReportService.getMyReports(userDetails.getUserNo());
        model.addAttribute("reports", reports);
        return "npc/my_reports";
    }
}