package com.samsamgyeesam.studyingvally.domain.admin.service;

import com.samsamgyeesam.studyingvally.domain.admin.dto.contact.AdminContactAnswerRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.contact.AdminContactDetailDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.contact.AdminContactListDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.notice.AdminNoticeDetailDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.notice.AdminNoticeListDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.notice.AdminNoticeRegistRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.notice.AdminNoticeUpdateRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.report.AdminReportAnswerRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.report.AdminReportDetailDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.report.AdminReportListDTO;
import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminNotice;
import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminQuestionTech;
import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminReport;
import com.samsamgyeesam.studyingvally.domain.admin.repository.AdminNoticeRepository;
import com.samsamgyeesam.studyingvally.domain.admin.repository.AdminQuestionTechRepository;
import com.samsamgyeesam.studyingvally.domain.admin.repository.AdminReportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/* comment.
 * 관리자 고객센터 서비스
 *
 * 왜 필요한가:
 * - 공지사항, 문의함, 신고함 관련 비즈니스 로직을 처리한다.
 * - 기존 공지사항 기능을 유지하면서 문의함 기능을 확장한다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCallCenterService {

    private final AdminNoticeRepository adminNoticeRepository;
    private final AdminQuestionTechRepository adminQuestionTechRepository;
    private final AdminReportRepository adminReportRepository;

    /* =========================
     * 공지사항
     * ========================= */

    /* comment.
     * 공지사항 목록 조회
     */
    public List<AdminNoticeListDTO> findAllNoticeList() {
        List<AdminNotice> noticeList = adminNoticeRepository.findAllByOrderByNoticeNoDesc();
        List<AdminNoticeListDTO> result = new ArrayList<>();

        int displayNo = noticeList.size();

        for (AdminNotice notice : noticeList) {
            result.add(new AdminNoticeListDTO(
                    notice.getNoticeNo(),
                    notice.getNoticeTitle(),
                    notice.getCreatedDate()
            ));
        }

        return result;
    }

    /* comment.
     * 공지사항 상세 조회
     */
    public AdminNoticeDetailDTO findNoticeDetail(Long noticeNo) {
        AdminNotice notice = adminNoticeRepository.findById(noticeNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 공지사항이 존재하지 않습니다. noticeNo=" + noticeNo));

        return new AdminNoticeDetailDTO(
                notice.getNoticeNo(),
                notice.getNoticeTitle(),
                notice.getNoticeDesc(),
                notice.getCreatedDate(),
                notice.getModifiedDate()
        );
    }

    /* comment.
     * 공지사항 등록
     */
    @Transactional
    public void registNotice(AdminNoticeRegistRequestDTO requestDTO) {
        validateNoticeInput(requestDTO.getNoticeTitle(), requestDTO.getNoticeDesc());

        AdminNotice adminNotice = AdminNotice.createNotice(
                requestDTO.getNoticeTitle().trim(),
                requestDTO.getNoticeDesc().trim()
        );

        adminNoticeRepository.save(adminNotice);
    }

    /* comment.
     * 공지사항 수정
     */
    @Transactional
    public void updateNotice(AdminNoticeUpdateRequestDTO requestDTO) {
        if (requestDTO.getNoticeNo() == null) {
            throw new IllegalArgumentException("공지사항 번호는 필수입니다.");
        }

        validateNoticeInput(requestDTO.getNoticeTitle(), requestDTO.getNoticeDesc());

        AdminNotice adminNotice = adminNoticeRepository.findById(requestDTO.getNoticeNo())
                .orElseThrow(() -> new EntityNotFoundException("수정할 공지사항이 존재하지 않습니다. noticeNo=" + requestDTO.getNoticeNo()));

        adminNotice.updateNotice(
                requestDTO.getNoticeTitle().trim(),
                requestDTO.getNoticeDesc().trim()
        );
    }

    /* comment.
     * 공지사항 입력값 검증
     */
    private void validateNoticeInput(String noticeTitle, String noticeDesc) {
        if (noticeTitle == null || noticeTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("공지사항 제목은 필수입니다.");
        }

        if (noticeDesc == null || noticeDesc.trim().isEmpty()) {
            throw new IllegalArgumentException("공지사항 내용은 필수입니다.");
        }

        if (noticeTitle.trim().length() > 255) {
            throw new IllegalArgumentException("공지사항 제목은 255자를 초과할 수 없습니다.");
        }
    }

    /* =========================
     * 문의함
     * ========================= */

    /* comment.
     * 문의함 목록 조회
     */
    public List<AdminContactListDTO> findAllContactList() {
        List<AdminQuestionTech> contactList = adminQuestionTechRepository.findAllWithUserOrderByQuestionTechNoDesc();
        List<AdminContactListDTO> result = new ArrayList<>();

        for (AdminQuestionTech contact : contactList) {
            result.add(new AdminContactListDTO(
                    contact.getQuestionTechNo(),
                    contact.getQuestionTitle(),
                    contact.getUser().getUserName(),
                    contact.getUser().getUserNickname(),
                    convertQuestionStatusToKorean(contact.getQuestionStatus()),
                    contact.getQuestionAnsweredAt()
            ));
        }

        return result;
    }

    /* comment.
     * 문의 상세 조회
     */
    public AdminContactDetailDTO findContactDetail(Long questionTechNo) {
        AdminQuestionTech contact = adminQuestionTechRepository.findDetailByQuestionTechNo(questionTechNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 문의가 존재하지 않습니다. questionTechNo=" + questionTechNo));

        return new AdminContactDetailDTO(
                contact.getQuestionTechNo(),
                contact.getQuestionTitle(),
                contact.getQuestionDesc(),
                contact.getUser().getUserName(),
                contact.getUser().getUserNickname(),
                convertQuestionStatusToKorean(contact.getQuestionStatus()),
                contact.getQuestionAnswer(),
                contact.getQuestionAnsweredAt(),
                contact.getQuestionAnswerUpdatedAt()
        );
    }

    /* comment.
     * 문의 답변 처리
     */
    @Transactional
    public void answerContact(AdminContactAnswerRequestDTO requestDTO) {
        if (requestDTO.getQuestionTechNo() == null) {
            throw new IllegalArgumentException("문의 번호는 필수입니다.");
        }

        AdminQuestionTech contact = adminQuestionTechRepository.findById(requestDTO.getQuestionTechNo())
                .orElseThrow(() -> new EntityNotFoundException("답변할 문의가 존재하지 않습니다. questionTechNo=" + requestDTO.getQuestionTechNo()));

        Long adminNo = 1L;
        contact.answerQuestion(requestDTO.getQuestionAnswer(), adminNo);
    }

    /* comment.
     * 상태 한글 변환
     */
    private String convertQuestionStatusToKorean(String questionStatus) {
        if ("RESOLVED".equals(questionStatus)) {
            return "완료";
        }
        return "대기";
    }

    /* =========================
     * 신고함
     * ========================= */

    /* comment.
     * 신고함 목록 조회
     */
    public List<AdminReportListDTO> findAllReportList() {
        List<AdminReport> reportList = adminReportRepository.findAllWithUserOrderByReportNoDesc();
        List<AdminReportListDTO> result = new ArrayList<>();

        for (AdminReport report : reportList) {
            result.add(new AdminReportListDTO(
                    report.getReportNo(),
                    report.getReportTitle(),
                    report.getUser().getUserName(),
                    report.getUser().getUserNickname(),
                    convertReportStatusToKorean(report.getReportStatus()),
                    report.getReportProcessedAt()
            ));
        }

        return result;
    }

    /* comment.
     * 신고 상세 조회
     *
     * 동작 순서:
     * 1. 신고와 사용자 정보를 함께 조회한다.
     * 2. 없으면 예외를 발생시킨다.
     * 3. 상세 DTO로 변환한다.
     */
    public AdminReportDetailDTO findReportDetail(Long reportNo) {
        AdminReport report = adminReportRepository.findDetailByReportNo(reportNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 신고가 존재하지 않습니다. reportNo=" + reportNo));

        return new AdminReportDetailDTO(
                report.getReportNo(),
                report.getReportTitle(),
                report.getReportDesc(),
                report.getUser().getUserName(),
                report.getUser().getUserNickname(),
                convertReportStatusToKorean(report.getReportStatus()),
                report.getReportAnswer(),
                report.getReportProcessedAt(),
                report.getReportAnswerUpdatedAt()
        );
    }

    /* comment.
     * 신고 답변 처리
     *
     * 동작 순서:
     * 1. 신고 번호 유효성 검증
     * 2. 수정 대상 조회
     * 3. 엔티티 내부 메서드로 답변 처리
     *
     * 주의할 점:
     * - 현재는 관리자 번호를 1L로 고정한다.
     * - 추후 로그인 관리자 세션에서 실제 관리자 번호를 받아오도록 교체하면 된다.
     */
    @Transactional
    public void answerReport(AdminReportAnswerRequestDTO requestDTO) {
        if (requestDTO.getReportNo() == null) {
            throw new IllegalArgumentException("신고 번호는 필수입니다.");
        }

        AdminReport report = adminReportRepository.findById(requestDTO.getReportNo())
                .orElseThrow(() -> new EntityNotFoundException("답변할 신고가 존재하지 않습니다. reportNo=" + requestDTO.getReportNo()));

        Long adminNo = 1L;
        report.answerReport(requestDTO.getReportAnswer(), adminNo);
    }

    /* comment.
     * 신고 상태 영문값을 화면용 한글로 변환
     *
     * 예:
     * - PENDING  -> 대기
     * - RESOLVED -> 완료
     */
    private String convertReportStatusToKorean(String reportStatus) {
        if ("RESOLVED".equals(reportStatus)) {
            return "완료";
        }

        return "대기";
    }
}