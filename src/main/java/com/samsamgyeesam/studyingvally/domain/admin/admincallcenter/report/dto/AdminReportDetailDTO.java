package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminReportDetailDTO {

    private Long reportNo;
    private String reportTitle;
    private String reportDesc;
    private String userName;
    private String userNickname;
    private String reportStatusLabel;
    private String reportAnswer;
    private LocalDateTime reportProcessedAt;
}