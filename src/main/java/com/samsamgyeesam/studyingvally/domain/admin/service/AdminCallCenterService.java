package com.samsamgyeesam.studyingvally.domain.admin.service;

import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminContactListDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.notice.AdminNoticeDetailDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.notice.AdminNoticeListDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.notice.AdminNoticeRegistRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.notice.AdminNoticeUpdateRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminReportListDTO;
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

/**
 * 관리자 고객센터 서비스 클래스
 *
 * 왜 필요한가:
 * - Controller와 Repository 사이에서 비즈니스 로직을 담당한다.
 * - 단순 조회뿐 아니라 등록/수정/검증/예외 처리까지 여기서 수행한다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCallCenterService {

    private final AdminNoticeRepository adminNoticeRepository;
    private final AdminQuestionTechRepository adminQuestionTechRepository;
    private final AdminReportRepository adminReportRepository;

    /**
     * 공지사항 목록 조회
     *
     * 동작 순서:
     * 1. DB에서 공지사항 전체를 번호 내림차순으로 조회한다.
     * 2. 화면용 DTO 리스트로 변환한다.
     * 3. 목록 번호(displayNo)를 별도로 계산하여 내려준다.
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

    /**
     * 공지사항 상세 조회
     *
     * 주의할 점:
     * - 존재하지 않는 번호가 들어오면 예외를 발생시킨다.
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

    /**
     * 공지사항 등록
     *
     * 동작 순서:
     * 1. 제목/내용 유효성 검사를 수행한다.
     * 2. 엔티티를 생성한다.
     * 3. 저장한다.
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

    /**
     * 공지사항 수정
     *
     * 동작 순서:
     * 1. 입력값 유효성 검사를 수행한다.
     * 2. 수정 대상 공지사항을 조회한다.
     * 3. 엔티티 내부 수정 메서드를 호출한다.
     * 4. 트랜잭션 종료 시 dirty checking으로 update SQL이 반영된다.
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

    /**
     * 공지사항 입력값 검증
     *
     * 왜 필요한가:
     * - 제목/내용이 비어있는 상태로 저장되는 것을 막기 위함이다.
     * - Controller가 아니라 Service에서도 한 번 더 막아야 안전하다.
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

    /**
     * 문의함 목록 조회
     */
    public List<AdminContactListDTO> findAllContactList() {
        List<AdminQuestionTech> contactList = adminQuestionTechRepository.findAllByOrderByQuestionTechNoDesc();
        List<AdminContactListDTO> result = new ArrayList<>();

        int displayNo = contactList.size();

        for (AdminQuestionTech contact : contactList) {
            result.add(new AdminContactListDTO(
                    displayNo,
                    contact.getQuestionTechNo(),
                    contact.getQuestionTitle(),
                    contact.getQuestionDesc(),
                    contact.getCourseId(),
                    contact.getUserNo(),
                    contact.getQuestionStatus(),
                    contact.getQuestionAnswer(),
                    contact.getAnsweredAdminNo(),
                    contact.getQuestionAnsweredAt(),
                    contact.getQuestionAnswerUpdatedAt()
            ));
            displayNo--;
        }

        return result;
    }

    /**
     * 신고함 목록 조회
     */
    public List<AdminReportListDTO> findAllReportList() {
        List<AdminReport> reportList = adminReportRepository.findAllByOrderByReportNoDesc();
        List<AdminReportListDTO> result = new ArrayList<>();

        int displayNo = reportList.size();

        for (AdminReport report : reportList) {
            result.add(new AdminReportListDTO(
                    displayNo,
                    report.getReportNo(),
                    report.getReportTitle(),
                    report.getReportDesc(),
                    report.getUserNo(),
                    report.getReportStatus(),
                    report.getReportAnswer(),
                    report.getProcessedAdminNo(),
                    report.getReportProcessedAt(),
                    report.getReportAnswerUpdatedAt()
            ));
            displayNo--;
        }

        return result;
    }
}