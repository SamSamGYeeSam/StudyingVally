package com.samsamgyeesam.studyingvally.domain.npc.controller;

import com.samsamgyeesam.studyingvally.domain.npc.dto.UserNpcQuestionTechDTO;
import com.samsamgyeesam.studyingvally.domain.npc.entity.UserNpcQuestionTech;
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
@RequestMapping("/npc") // URL 경로는 기존과 동일하게 유지
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

        Long userNo = userDetails.getUserNo();

        userNpcService.registInquiry(dto, userNo);

        rttr.addFlashAttribute("message", "문의가 성공적으로 접수되었습니다. NPC가 곧 답변해 드릴 예정입니다!");

        return "redirect:/npc";
    }

    @GetMapping("/my-inquiries")
    public String myInquiries(@AuthenticationPrincipal AuthUserDetails userDetails, Model model) {
        Long userNo = userDetails.getUserNo();

        // ✨ [수정됨] 이제 서비스가 Entity가 아닌 DTO 리스트를 안전하게 넘겨줍니다.
        List<UserNpcQuestionTechDTO> inquiries = userNpcService.getMyInquiries(userNo);
        model.addAttribute("inquiries", inquiries);

        return "npc/my_inquiries";
    }
}