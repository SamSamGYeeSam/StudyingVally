package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminReportListDTO {

    private Long reportNo;
    private String reportTitle;
    private String userName;
    private String userNickname;
    private String reportStatusLabel;
    private LocalDateTime reportProcessedAt;
}