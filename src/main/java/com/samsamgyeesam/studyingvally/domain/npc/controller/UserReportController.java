package com.samsamgyeesam.studyingvally.domain.npc.controller;

import com.samsamgyeesam.studyingvally.domain.npc.dto.UserReportDTO;
import com.samsamgyeesam.studyingvally.domain.npc.exception.NpcException;
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
@RequestMapping("/npc/report")
public class UserReportController {

    private final UserReportService userReportService;

    @GetMapping
    public String reportForm() {
        return "npc/report_form";
    }

    @PostMapping
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

    @GetMapping("/my-reports")
    public String myReports(@AuthenticationPrincipal AuthUserDetails userDetails, Model model) {
        List<UserReportDTO> reports = userReportService.getMyReports(userDetails.getUserNo());
        model.addAttribute("reports", reports);
        return "npc/my_reports";
    }
}