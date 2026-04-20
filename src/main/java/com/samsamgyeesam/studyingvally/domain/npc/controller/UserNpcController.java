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
        // HTML에서 분기 처리를 할 수 있도록 사용자의 역할을 'role'이라는 이름으로 넘겨줍니다.
        // (주의: AuthUserDetails 클래스에 getRole() 메서드가 있다고 가정했습니다.
        // 만약 이름이 다르다면 userDetails.getUserRole() 등으로 수정해 주세요.)
        model.addAttribute("role", userDetails.getRole().toString());
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