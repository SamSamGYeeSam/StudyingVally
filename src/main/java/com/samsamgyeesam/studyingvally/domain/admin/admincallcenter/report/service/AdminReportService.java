package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.service;

import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.dto.AdminReportAnswerRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.dto.AdminReportDetailDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.dto.AdminReportListDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.entity.AdminReport;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.repository.AdminReportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 관리자 신고함 서비스
 *
 * 왜 필요한가:
 * - 신고 목록 조회, 상세 조회, 답변 처리 비즈니스 로직을 담당한다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminReportService {

    /**
     * 신고 Repository
     */
    private final AdminReportRepository adminReportRepository;

    /**
     * 신고 목록 조회
     *
     * @return 신고 목록 DTO 리스트
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

    /**
     * 신고 상세 조회
     *
     * @param reportNo 신고 번호
     * @return 신고 상세 DTO
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

    /**
     * 신고 답변 처리
     *
     * @param requestDTO 신고 답변 요청 DTO
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

    /**
     * 신고 상태 한글 변환
     *
     * @param reportStatus 원본 상태값
     * @return 화면 출력용 한글 상태값
     */
    private String convertReportStatusToKorean(String reportStatus) {
        if ("RESOLVED".equals(reportStatus)) {
            return "완료";
        }

        return "대기";
    }
}