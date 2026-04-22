package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.service;

import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.dto.AdminReportAnswerRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.dto.AdminReportDetailDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.dto.AdminReportListDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.entity.AdminReport;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.repository.AdminReportRepository;
import com.samsamgyeesam.studyingvally.domain.admin.exception.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminReportService {

    private final AdminReportRepository adminReportRepository;

    public List<AdminReportListDTO> findAllReportList() {
        return adminReportRepository.findAllWithUserOrderByReportNoDesc()
                .stream()
                .map(report -> new AdminReportListDTO(
                        report.getReportNo(),
                        report.getReportTitle(),
                        report.getUser().getUserName(),
                        report.getUser().getUserNickname(),
                        convertReportStatusToKorean(report.getReportStatus()),
                        report.getReportProcessedAt()
                ))
                .collect(Collectors.toList());
    }

    public AdminReportDetailDTO findReportDetail(Long reportNo) {
        AdminReport report = adminReportRepository.findDetailByReportNo(reportNo)
                .orElseThrow(() -> new AdminException("해당 신고가 존재하지 않습니다."));

        return new AdminReportDetailDTO(
                report.getReportNo(),
                report.getReportTitle(),
                report.getReportDesc(),
                report.getUser().getUserName(),
                report.getUser().getUserNickname(),
                convertReportStatusToKorean(report.getReportStatus()),
                report.getReportAnswer(),
                report.getReportProcessedAt()
        );
    }

    @Transactional
    public void answerReport(AdminReportAnswerRequestDTO requestDTO) {
        if (requestDTO.getReportNo() == null) {
            throw new AdminException("신고 번호는 필수입니다.");
        }

        if (requestDTO.getReportAnswer() == null || requestDTO.getReportAnswer().trim().isEmpty()) {
            throw new AdminException("답변 내용을 입력해 주세요.");
        }

        AdminReport report = adminReportRepository.findById(requestDTO.getReportNo())
                .orElseThrow(() -> new AdminException("답변할 신고가 존재하지 않습니다."));

        report.answerReport(requestDTO.getReportAnswer());
    }

    private String convertReportStatusToKorean(String reportStatus) {
        if ("RESOLVED".equals(reportStatus)) {
            return "완료";
        }
        return "대기";
    }
}