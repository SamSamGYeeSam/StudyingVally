package com.samsamgyeesam.studyingvally.domain.npc.service;

import com.samsamgyeesam.studyingvally.domain.npc.dto.UserReportDTO;
import com.samsamgyeesam.studyingvally.domain.npc.entity.UserReport;
import com.samsamgyeesam.studyingvally.domain.npc.repository.UserReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserReportService {

    private final UserReportRepository userReportRepository;

    @Transactional
    public void registReport(UserReportDTO dto, Long userNo) {
        UserReport entity = new UserReport();
        entity.setReportTitle(dto.getReportTitle());
        entity.setReportDesc(dto.getReportDesc());
        entity.setUserNo(userNo);
        entity.setReportStatus("처리 대기"); // 신고 초기 상태 설정

        userReportRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<UserReportDTO> getMyReports(Long userNo) {
        List<UserReport> entities = userReportRepository.findByUserNoOrderByReportNoDesc(userNo);

        return entities.stream().map(entity -> {
            UserReportDTO dto = new UserReportDTO();
            dto.setReportNo(entity.getReportNo());
            dto.setReportTitle(entity.getReportTitle());
            dto.setReportDesc(entity.getReportDesc());
            dto.setReportStatus(entity.getReportStatus());
            dto.setReportAnswer(entity.getReportAnswer());
            return dto;
        }).collect(Collectors.toList());
    }
}