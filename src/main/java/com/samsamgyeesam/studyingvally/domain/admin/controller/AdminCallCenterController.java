package com.samsamgyeesam.studyingvally.domain.admin.controller;

import com.samsamgyeesam.studyingvally.domain.admin.dto.notice.AdminNoticeRegistRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.notice.AdminNoticeUpdateRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.service.AdminCallCenterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 관리자 고객센터 컨트롤러
 *
 * 왜 필요한가:
 * - 관리자 고객센터 화면 요청을 처리한다.
 * - 공지사항 목록/상세/등록/수정 화면과 요청 흐름을 담당한다.
 */
@Controller
@RequestMapping("/admin/callcenter")
@RequiredArgsConstructor
public class AdminCallCenterController {

    private final AdminCallCenterService adminCallCenterService;

    /**
     * 고객센터 메인 페이지 조회
     */
    @GetMapping
    public String callCenterMainPage(Model model) {
        model.addAttribute("pageTitle", "고객센터 관리 페이지");
        return "admin/callcenter";
    }

    /**
     * 공지사항 목록 조회
     */
    @GetMapping("/notice")
    public String findAllNoticeList(Model model) {
        model.addAttribute("pageTitle", "공지사항 목록");
        model.addAttribute("noticeList", adminCallCenterService.findAllNoticeList());
        return "admin/noticelist";
    }

    /**
     * 문의함 목록 조회
     */
    @GetMapping("/contact")
    public String findAllContactList(Model model) {
        model.addAttribute("pageTitle", "문의함 목록");
        model.addAttribute("contactList", adminCallCenterService.findAllContactList());
        return "admin/contactlist";
    }

    /**
     * 신고함 목록 조회
     */
    @GetMapping("/report")
    public String findAllReportList(Model model) {
        model.addAttribute("pageTitle", "신고함 목록");
        model.addAttribute("reportList", adminCallCenterService.findAllReportList());
        return "admin/reportlist";
    }

    /**
     * 공지사항 등록 페이지 조회
     *
     * 동작 이유:
     * - 등록 화면에 빈 폼 객체를 내려주기 위함이다.
     */
    @GetMapping("/noticeregist")
    public String noticeRegistPage(Model model) {
        model.addAttribute("pageTitle", "공지사항 추가");
        model.addAttribute("noticeRegistRequest", new AdminNoticeRegistRequestDTO());
        return "admin/noticeregist";
    }

    /*
     * 공지사항 등록 처리  */
    @PostMapping("/noticeregist")
    public String registNotice(@ModelAttribute("noticeRegistRequest") AdminNoticeRegistRequestDTO requestDTO,
                               RedirectAttributes redirectAttributes) {
        try {
            adminCallCenterService.registNotice(requestDTO);
            redirectAttributes.addAttribute("result", "success");
            return "redirect:/admin/callcenter/notice";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/noticeregist";
        }
    }

    /*
     * 공지사항 상세 페이지 조회*/
    @GetMapping("/noticedetail")
    public String noticeDetail(@RequestParam("noticeNo") Long noticeNo, Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("pageTitle", "공지사항 상세");
            model.addAttribute("noticeDetail", adminCallCenterService.findNoticeDetail(noticeNo));
            return "admin/noticedetail";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/notice";
        }
    }

    /*
     * 공지사항 수정 처리
     */

    @PostMapping("/noticedetail")
    public String updateNotice(@ModelAttribute("noticeDetail") AdminNoticeUpdateRequestDTO requestDTO,
                               RedirectAttributes redirectAttributes) {
        try {
            adminCallCenterService.updateNotice(requestDTO);
            redirectAttributes.addFlashAttribute("message", "공지사항이 정상적으로 수정되었습니다.");
            return "redirect:/admin/callcenter/notice";
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/noticedetail?noticeNo=" + requestDTO.getNoticeNo();
        }
    }
}