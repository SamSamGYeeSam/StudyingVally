package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.controller;

import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.dto.AdminContactAnswerRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.service.AdminContactService;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.dto.AdminNoticeRegistRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.dto.AdminNoticeUpdateRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.service.AdminNoticeService;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.dto.AdminReportAnswerRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.service.AdminReportService;
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
 * - 고객센터 메인, 공지사항, 문의함, 신고함 화면 요청을 처리하기 위함이다.
 *
 * 설계 방향:
 * - 컨트롤러는 하나로 유지하되,
 * - 실제 비즈니스 로직은 공지, 문의, 신고 서비스로 분리한다.
 */
@Controller
@RequestMapping("/admin/callcenter")
@RequiredArgsConstructor
public class AdminCallCenterController {

    /**
     * 공지사항 서비스
     */
    private final AdminNoticeService adminNoticeService;

    /**
     * 문의 서비스
     */
    private final AdminContactService adminContactService;

    /**
     * 신고 서비스
     */
    private final AdminReportService adminReportService;

    /**
     * 고객센터 메인 페이지 조회
     *
     * @param model 뷰 전달 객체
     * @return 고객센터 메인 템플릿 경로
     */
    @GetMapping
    public String callCenterMainPage(Model model) {
        model.addAttribute("pageTitle", "고객센터 관리 페이지");
        return "admin/callcenter";
    }

    /**
     * 공지사항 목록 페이지 조회
     *
     * @param model 뷰 전달 객체
     * @return 공지사항 목록 템플릿 경로
     */
    @GetMapping("/notice")
    public String findAllNoticeList(Model model) {
        model.addAttribute("pageTitle", "공지사항 목록");
        model.addAttribute("noticeList", adminNoticeService.findAllNoticeList());
        return "admin/noticelist";
    }

    /**
     * 공지사항 등록 페이지 조회
     *
     * @param model 뷰 전달 객체
     * @return 공지사항 등록 템플릿 경로
     */
    @GetMapping("/noticeregist")
    public String noticeRegistPage(Model model) {
        model.addAttribute("pageTitle", "공지사항 추가");
        model.addAttribute("noticeRegistRequest", new AdminNoticeRegistRequestDTO());
        return "admin/noticeregist";
    }

    /**
     * 공지사항 등록 처리
     *
     * @param requestDTO 등록 요청 DTO
     * @param redirectAttributes 리다이렉트 메시지 객체
     * @return 리다이렉트 경로
     */
    @PostMapping("/noticeregist")
    public String registNotice(@ModelAttribute("noticeRegistRequest") AdminNoticeRegistRequestDTO requestDTO,
                               RedirectAttributes redirectAttributes) {
        try {
            adminNoticeService.registNotice(requestDTO);
            return "redirect:/admin/callcenter/notice";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/noticeregist";
        }
    }

    /**
     * 공지사항 상세 조회
     *
     * @param noticeNo 공지 번호
     * @param model 뷰 전달 객체
     * @param redirectAttributes 리다이렉트 메시지 객체
     * @return 공지사항 상세 템플릿 경로 또는 리다이렉트 경로
     */
    @GetMapping("/noticedetail")
    public String noticeDetail(@RequestParam("noticeNo") Long noticeNo,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("pageTitle", "공지사항 상세");
            model.addAttribute("noticeDetail", adminNoticeService.findNoticeDetail(noticeNo));
            return "admin/noticedetail";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/notice";
        }
    }

    /**
     * 공지사항 수정 처리
     *
     * @param requestDTO 수정 요청 DTO
     * @param redirectAttributes 리다이렉트 메시지 객체
     * @return 리다이렉트 경로
     */
    @PostMapping("/noticedetail")
    public String updateNotice(@ModelAttribute AdminNoticeUpdateRequestDTO requestDTO,
                               RedirectAttributes redirectAttributes) {
        try {
            adminNoticeService.updateNotice(requestDTO);
            return "redirect:/admin/callcenter/notice";
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/noticedetail?noticeNo=" + requestDTO.getNoticeNo();
        }
    }

    /**
     * 문의함 목록 페이지 조회
     *
     * @param model 뷰 전달 객체
     * @return 문의함 목록 템플릿 경로
     */
    @GetMapping("/contact")
    public String findAllContactList(Model model) {
        model.addAttribute("pageTitle", "문의함 목록");
        model.addAttribute("contactList", adminContactService.findAllContactList());
        return "admin/contactlist";
    }

    /**
     * 문의 상세 페이지 조회
     *
     * @param questionTechNo 문의 번호
     * @param model 뷰 전달 객체
     * @param redirectAttributes 리다이렉트 메시지 객체
     * @return 문의 상세 템플릿 경로 또는 리다이렉트 경로
     */
    @GetMapping("/contactdetail")
    public String contactDetail(@RequestParam("questionTechNo") Long questionTechNo,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("pageTitle", "문의 상세");
            model.addAttribute("contactDetail", adminContactService.findContactDetail(questionTechNo));
            return "admin/contactdetail";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/contact";
        }
    }

    /**
     * 문의 답변 처리
     *
     * @param requestDTO 문의 답변 요청 DTO
     * @param redirectAttributes 리다이렉트 메시지 객체
     * @return 리다이렉트 경로
     */
    @PostMapping("/contactdetail")
    public String answerContact(@ModelAttribute AdminContactAnswerRequestDTO requestDTO,
                                RedirectAttributes redirectAttributes) {
        try {
            adminContactService.answerContact(requestDTO);
            return "redirect:/admin/callcenter/contact";
        } catch (IllegalArgumentException | IllegalStateException | EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/contactdetail?questionTechNo=" + requestDTO.getQuestionTechNo();
        }
    }

    /**
     * 신고함 목록 페이지 조회
     *
     * @param model 뷰 전달 객체
     * @return 신고함 목록 템플릿 경로
     */
    @GetMapping("/report")
    public String findAllReportList(Model model) {
        model.addAttribute("pageTitle", "신고함 목록");
        model.addAttribute("reportList", adminReportService.findAllReportList());
        return "admin/reportlist";
    }

    /**
     * 신고 상세 페이지 조회
     *
     * @param reportNo 신고 번호
     * @param model 뷰 전달 객체
     * @param redirectAttributes 리다이렉트 메시지 객체
     * @return 신고 상세 템플릿 경로 또는 리다이렉트 경로
     */
    @GetMapping("/reportdetail")
    public String reportDetail(@RequestParam("reportNo") Long reportNo,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("pageTitle", "신고 상세");
            model.addAttribute("reportDetail", adminReportService.findReportDetail(reportNo));
            return "admin/reportdetail";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/report";
        }
    }

    /**
     * 신고 답변 처리
     *
     * @param requestDTO 신고 답변 요청 DTO
     * @param redirectAttributes 리다이렉트 메시지 객체
     * @return 리다이렉트 경로
     */
    @PostMapping("/reportdetail")
    public String answerReport(@ModelAttribute AdminReportAnswerRequestDTO requestDTO,
                               RedirectAttributes redirectAttributes) {
        try {
            adminReportService.answerReport(requestDTO);
            return "redirect:/admin/callcenter/report";
        } catch (IllegalArgumentException | IllegalStateException | EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/reportdetail?reportNo=" + requestDTO.getReportNo();
        }
    }
}