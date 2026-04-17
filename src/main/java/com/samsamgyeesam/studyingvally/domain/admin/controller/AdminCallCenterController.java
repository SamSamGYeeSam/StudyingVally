package com.samsamgyeesam.studyingvally.domain.admin.controller;

import com.samsamgyeesam.studyingvally.domain.admin.dto.contact.AdminContactAnswerRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.notice.AdminNoticeRegistRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.notice.AdminNoticeUpdateRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.report.AdminReportAnswerRequestDTO;
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

/* comment.
 * 관리자 고객센터 컨트롤러
 *
 * 왜 필요한가:
 * - 공지사항, 문의함, 신고함 관련 화면 요청을 처리한다.
 * - 기존 공지사항 기능을 유지하면서 문의함 기능을 함께 추가한다.
 */
@Controller
@RequestMapping("/admin/callcenter")
@RequiredArgsConstructor
public class AdminCallCenterController {

    private final AdminCallCenterService adminCallCenterService;

    /* comment.
     * 고객센터 메인 페이지
     */
    @GetMapping
    public String callCenterMainPage(Model model) {
        model.addAttribute("pageTitle", "고객센터 관리 페이지");
        return "admin/callcenter";
    }

    /* =========================
     * 공지사항
     * ========================= */

    /* comment.
     * 공지사항 목록 페이지
     */
    @GetMapping("/notice")
    public String findAllNoticeList(Model model) {
        model.addAttribute("pageTitle", "공지사항 목록");
        model.addAttribute("noticeList", adminCallCenterService.findAllNoticeList());
        return "admin/noticelist";
    }

    /* comment.
     * 공지사항 등록 페이지
     */
    @GetMapping("/noticeregist")
    public String noticeRegistPage(Model model) {
        model.addAttribute("pageTitle", "공지사항 추가");
        model.addAttribute("noticeRegistRequest", new AdminNoticeRegistRequestDTO());
        return "admin/noticeregist";
    }

    /* comment.
     * 공지사항 등록 처리
     */
    @PostMapping("/noticeregist")
    public String registNotice(@ModelAttribute("noticeRegistRequest") AdminNoticeRegistRequestDTO requestDTO,
                               RedirectAttributes redirectAttributes) {
        try {
            adminCallCenterService.registNotice(requestDTO);
            return "redirect:/admin/callcenter/notice";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/noticeregist";
        }
    }

    /* comment.
     * 공지사항 상세 페이지
     */
    @GetMapping("/noticedetail")
    public String noticeDetail(@RequestParam("noticeNo") Long noticeNo,
                               Model model,
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

    /* comment.
     * 공지사항 수정 처리
     */
    @PostMapping("/noticedetail")
    public String updateNotice(@ModelAttribute AdminNoticeUpdateRequestDTO requestDTO,
                               RedirectAttributes redirectAttributes) {
        try {
            adminCallCenterService.updateNotice(requestDTO);
            return "redirect:/admin/callcenter/notice";
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/noticedetail?noticeNo=" + requestDTO.getNoticeNo();
        }
    }

    /* =========================
     * 문의함
     * ========================= */

    /* comment.
     * 문의함 목록 페이지
     */
    @GetMapping("/contact")
    public String findAllContactList(Model model) {
        model.addAttribute("pageTitle", "문의함 목록");
        model.addAttribute("contactList", adminCallCenterService.findAllContactList());
        return "admin/contactlist";
    }

    /* comment.
     * 문의 상세 페이지
     */
    @GetMapping("/contactdetail")
    public String contactDetail(@RequestParam("questionTechNo") Long questionTechNo,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("pageTitle", "문의 상세");
            model.addAttribute("contactDetail", adminCallCenterService.findContactDetail(questionTechNo));
            return "admin/contactdetail";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/contact";
        }
    }

    /* comment.
     * 문의 답변 완료 처리
     */
    @PostMapping("/contactdetail")
    public String answerContact(@ModelAttribute AdminContactAnswerRequestDTO requestDTO,
                                RedirectAttributes redirectAttributes) {
        try {
            adminCallCenterService.answerContact(requestDTO);
            return "redirect:/admin/callcenter/contact";
        } catch (IllegalArgumentException | IllegalStateException | EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/contactdetail?questionTechNo=" + requestDTO.getQuestionTechNo();
        }
    }

    /* =========================
     * 신고함
     * ========================= */

    /* comment.
     * 신고함 목록 페이지
     */
    @GetMapping("/report")
    public String findAllReportList(Model model) {
        model.addAttribute("pageTitle", "신고함 목록");
        model.addAttribute("reportList", adminCallCenterService.findAllReportList());
        return "admin/reportlist";
    }

    /* comment.
     * 신고함 상세 페이지
     *
     * 요청 파라미터를 @RequestParam으로 받는 이유:
     * - 목록 화면의 선택 버튼이 query parameter 방식으로 이동하기 때문이다.
     */
    @GetMapping("/reportdetail")
    public String reportDetail(@RequestParam("reportNo") Long reportNo,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("pageTitle", "신고 상세");
            model.addAttribute("reportDetail", adminCallCenterService.findReportDetail(reportNo));
            return "admin/reportdetail";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/report";
        }
    }

    /* comment.
     * 신고 답변 완료 처리
     *
     * 동작 원칙:
     * - 처리 후 redirect로 목록 페이지로 이동한다.
     * - 새로고침으로 인한 중복 제출을 막기 위해 PRG 패턴을 따른다.
     */
    @PostMapping("/reportdetail")
    public String answerReport(@ModelAttribute AdminReportAnswerRequestDTO requestDTO,
                               RedirectAttributes redirectAttributes) {
        try {
            adminCallCenterService.answerReport(requestDTO);
            return "redirect:/admin/callcenter/report";
        } catch (IllegalArgumentException | IllegalStateException | EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/callcenter/reportdetail?reportNo=" + requestDTO.getReportNo();
        }
    }
}