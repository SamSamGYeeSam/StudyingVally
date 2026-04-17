package com.samsamgyeesam.studyingvally.domain.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
@NoArgsConstructor
public class AdminReport {

    @Id
    @Column(name = "report_no")
    private Long reportNo;

    @Column(name = "report_title", nullable = false)
    private String reportTitle;

    @Column(name = "report_desc", nullable = false)
    private String reportDesc;

    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "report_status", nullable = false)
    private String reportStatus;

    @Column(name = "report_answer")
    private String reportAnswer;

    @Column(name = "processed_admin_no")
    private Long processedAdminNo;

    @Column(name = "report_processed_at")
    private LocalDateTime reportProcessedAt;

    @Column(name = "report_answer_updated_at")
    private LocalDateTime reportAnswerUpdatedAt;
}