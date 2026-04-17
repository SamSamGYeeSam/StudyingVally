package com.samsamgyeesam.studyingvally.domain.admin.service;

import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminContactListDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminNoticeListDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminReportListDTO;
import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminNotice;
import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminQuestionTech;
import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminReport;
import com.samsamgyeesam.studyingvally.domain.admin.repository.AdminNoticeRepository;
import com.samsamgyeesam.studyingvally.domain.admin.repository.AdminQuestionTechRepository;
import com.samsamgyeesam.studyingvally.domain.admin.repository.AdminReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCallCenterService {

    private final AdminNoticeRepository adminNoticeRepository;
    private final AdminQuestionTechRepository adminQuestionTechRepository;
    private final AdminReportRepository adminReportRepository;

    public List<AdminNoticeListDTO> findAllNoticeList() {

        List<AdminNotice> noticeList = adminNoticeRepository.findAllByOrderByNoticeNoDesc();
        List<AdminNoticeListDTO> result = new ArrayList<>();

        int displayNo = noticeList.size();

        for (AdminNotice notice : noticeList) {
            result.add(new AdminNoticeListDTO(
                    displayNo,
                    notice.getNoticeNo(),
                    notice.getNoticeTitle(),
                    notice.getNoticeDesc()
            ));
            displayNo--;
        }

        return result;
    }

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