package com.samsamgyeesam.studyingvally.domain.admin.adminusercare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "report_count")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminReportCount {

    @Id
    @Column(name = "report_count_no")
    private Long reportCountNo;

    @Column(name = "report_count", nullable = false)
    private int reportCount;

    @Column(name = "user_no", nullable = false)
    private Long userNo;
}