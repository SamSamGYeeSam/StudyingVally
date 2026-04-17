package com.samsamgyeesam.studyingvally.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminReportListDTO {

    private Integer displayNo;
    private Long reportNo;
    private String reportTitle;
    private String reportDesc;
    private Long userNo;
    private String reportStatus;
    private String reportAnswer;
    private Long processedAdminNo;
    private LocalDateTime reportProcessedAt;
    private LocalDateTime reportAnswerUpdatedAt;
}