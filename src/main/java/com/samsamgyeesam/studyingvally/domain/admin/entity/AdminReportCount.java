package com.samsamgyeesam.studyingvally.domain.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* comment.
 * 관리자 사용자 상세 조회에서 신고 횟수를 조회하기 위한 엔티티 클래스
 */

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