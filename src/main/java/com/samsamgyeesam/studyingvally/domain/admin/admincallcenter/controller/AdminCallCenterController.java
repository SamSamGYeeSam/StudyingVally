package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.controller;

import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.dto.AdminContactAnswerRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.service.AdminContactService;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.dto.AdminNoticeRegistRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.dto.AdminNoticeUpdateRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.service.AdminNoticeService;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.dto.AdminReportAnswerRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.service.AdminReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 관리자 고객센터 컨트롤러
 *
 * 왜 필요한가:
 * - 고객센터 메인, 공지사항, 문의함, 신고함 화면 요청을 처리하기 위함이다.
 *
 * 주의할 점:
 * - 예외 처리는 컨트롤러에서 직접 하지 않고 AdminExceptionHandler에서 일괄 처리한다.
 */
@Controller
@RequestMapping("/admin/callcenter")
@RequiredArgsConstructor
public class AdminCallCenterController {

    private final AdminNoticeService adminNoticeService;
    private final AdminContactService adminContactService;
    private final AdminReportService adminReportService;

    @GetMapping
    public String callCenterMainPage(Model model) {
        model.addAttribute("pageTitle", "고객센터 관리 페이지");
        return "admin/callcenter";
    }

    @GetMapping("/notice")
    public String findAllNoticeList(Model model) {
        model.addAttribute("pageTitle", "공지사항 목록");
        model.addAttribute("noticeList", adminNoticeService.findAllNoticeList());
        return "admin/noticelist";
    }

    @GetMapping("/noticeregist")
    public String noticeRegistPage(Model model) {
        model.addAttribute("pageTitle", "공지사항 추가");
        model.addAttribute("noticeRegistRequest", new AdminNoticeRegistRequestDTO());
        return "admin/noticeregist";
    }

    @PostMapping("/noticeregist")
    public String registNotice(@ModelAttribute("noticeRegistRequest") AdminNoticeRegistRequestDTO requestDTO) {
        adminNoticeService.registNotice(requestDTO);
        return "redirect:/admin/callcenter/notice";
    }

    @GetMapping("/noticedetail")
    public String noticeDetail(@RequestParam("noticeNo") Long noticeNo,
                               Model model) {
        model.addAttribute("pageTitle", "공지사항 상세");
        model.addAttribute("noticeDetail", adminNoticeService.findNoticeDetail(noticeNo));
        return "admin/noticedetail";
    }

    @PostMapping("/noticedetail")
    public String updateNotice(@ModelAttribute AdminNoticeUpdateRequestDTO requestDTO) {
        adminNoticeService.updateNotice(requestDTO);
        return "redirect:/admin/callcenter/notice";
    }

    @GetMapping("/contact")
    public String findAllContactList(Model model) {
        model.addAttribute("pageTitle", "문의함 목록");
        model.addAttribute("contactList", adminContactService.findAllContactList());
        return "admin/contactlist";
    }

    @GetMapping("/contactdetail")
    public String contactDetail(@RequestParam("questionTechNo") Long questionTechNo,
                                Model model) {
        model.addAttribute("pageTitle", "문의 상세");
        model.addAttribute("contactDetail", adminContactService.findContactDetail(questionTechNo));
        return "admin/contactdetail";
    }

    @PostMapping("/contactdetail")
    public String answerContact(@ModelAttribute AdminContactAnswerRequestDTO requestDTO) {
        adminContactService.answerContact(requestDTO);
        return "redirect:/admin/callcenter/contact";
    }

    @GetMapping("/report")
    public String findAllReportList(Model model) {
        model.addAttribute("pageTitle", "신고함 목록");
        model.addAttribute("reportList", adminReportService.findAllReportList());
        return "admin/reportlist";
    }

    @GetMapping("/reportdetail")
    public String reportDetail(@RequestParam("reportNo") Long reportNo,
                               Model model) {
        model.addAttribute("pageTitle", "신고 상세");
        model.addAttribute("reportDetail", adminReportService.findReportDetail(reportNo));
        return "admin/reportdetail";
    }

    @PostMapping("/reportdetail")
    public String answerReport(@ModelAttribute AdminReportAnswerRequestDTO requestDTO) {
        adminReportService.answerReport(requestDTO);
        return "redirect:/admin/callcenter/report";
    }
}