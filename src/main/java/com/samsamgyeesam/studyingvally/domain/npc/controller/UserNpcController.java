package com.samsamgyeesam.studyingvally.domain.npc.controller;

import com.samsamgyeesam.studyingvally.domain.npc.dto.UserNpcQuestionTechDTO;
import com.samsamgyeesam.studyingvally.domain.npc.exception.NpcException;
import com.samsamgyeesam.studyingvally.domain.npc.service.UserNpcService;
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

    @GetMapping
    public String npcMain() {
        return "npc/main";
    }

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
}