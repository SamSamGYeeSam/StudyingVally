package com.samsamgyeesam.studyingvally.domain.npc.service;

import com.samsamgyeesam.studyingvally.domain.npc.dto.UserReportDTO;
import com.samsamgyeesam.studyingvally.domain.npc.entity.UserReport;
import com.samsamgyeesam.studyingvally.domain.npc.exception.NpcException;
import com.samsamgyeesam.studyingvally.domain.npc.repository.UserReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserReportService {

    private final UserReportRepository repository;

    @Transactional
    public void registReport(UserReportDTO dto, Long userNo) {
        if (dto.getReportTitle() == null || dto.getReportTitle().trim().isEmpty()) {
            throw new NpcException("신고 대상을 포함한 제목을 입력해 주세요.");
        }
        if (dto.getReportDesc() == null || dto.getReportDesc().trim().isEmpty()) {
            throw new NpcException("신고 상세 내용을 입력해 주세요.");
        }

        UserReport entity = new UserReport();
        entity.setReportTitle(dto.getReportTitle());
        entity.setReportDesc(dto.getReportDesc());
        entity.setUserNo(userNo);
        entity.setReportStatus("처리 대기");

        repository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<UserReportDTO> getMyReports(Long userNo) {
        return repository.findByUserNoOrderByReportNoDesc(userNo).stream().map(entity -> {
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