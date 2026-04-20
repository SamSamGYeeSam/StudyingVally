package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.entity;

import com.samsamgyeesam.studyingvally.baseentity.BaseOnlyCreateTime;
import com.samsamgyeesam.studyingvally.domain.admin.adminusercare.entity.AdminUser;
import com.samsamgyeesam.studyingvally.domain.admin.exception.AdminException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminReport extends BaseOnlyCreateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_no")
    private Long reportNo;

    @Column(name = "report_title", nullable = false)
    private String reportTitle;

    @Column(name = "report_desc", nullable = false)
    private String reportDesc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private AdminUser user;

    @Column(name = "report_status", nullable = false)
    private String reportStatus;

    @Column(name = "report_answer")
    private String reportAnswer;

    @Column(name = "report_processed_at")
    private LocalDateTime reportProcessedAt;

    public void answerReport(String reportAnswer) {
        if (this.reportAnswer != null && !this.reportAnswer.trim().isEmpty()) {
            throw new AdminException("이미 답변이 완료된 신고입니다.");
        }

        if (reportAnswer == null || reportAnswer.trim().isEmpty()) {
            throw new AdminException("답변 내용을 입력해 주세요.");
        }

        this.reportAnswer = reportAnswer.trim();
        this.reportStatus = "RESOLVED";
        this.reportProcessedAt = LocalDateTime.now();
    }
}