package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.entity;

import com.samsamgyeesam.studyingvally.domain.admin.adminusercare.entity.AdminUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/* comment.
 * 관리자 신고함 기능에서 report 테이블을 조회/수정하기 위한 엔티티 클래스
 *
 * 왜 필요한가:
 * - 신고 목록 조회
 * - 신고 상세 조회
 * - 신고 답변 작성 및 처리 상태 변경
 *
 * 주의할 점:
 * - 신고자 이름/닉네임을 화면에 보여주기 위해 AdminUser와 연관관계를 맺는다.
 * - setter 대신 엔티티 내부 메서드로 상태를 변경한다.
 */
@Entity
@Table(name = "report")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminReport {

    /* comment.
     * 신고 PK
     */
    @Id
    @Column(name = "report_no")
    private Long reportNo;

    /* comment.
     * 신고 제목
     */
    @Column(name = "report_title", nullable = false)
    private String reportTitle;

    /* comment.
     * 신고 상세 내용
     */
    @Column(name = "report_desc", nullable = false)
    private String reportDesc;

    /* comment.
     * 신고 작성 사용자
     *
     * 왜 필요한가:
     * - 신고함 목록/상세 화면에서 유저 이름과 닉네임을 보여주기 위함이다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private AdminUser user;

    /* comment.
     * 신고 처리 상태
     *
     * 저장값 예시:
     * - PENDING
     * - RESOLVED
     */
    @Column(name = "report_status", nullable = false)
    private String reportStatus;

    /* comment.
     * 관리자 처리 답변
     */
    @Column(name = "report_answer")
    private String reportAnswer;

    /* comment.
     * 처리 관리자 번호
     */
    @Column(name = "processed_admin_no")
    private Long processedAdminNo;

    /* comment.
     * 최초 처리 완료 일시
     */
    @Column(name = "report_processed_at")
    private LocalDateTime reportProcessedAt;

    /* comment.
     * 답변 수정 일시
     */
    @Column(name = "report_answer_updated_at")
    private LocalDateTime reportAnswerUpdatedAt;

    /* comment.
     * 신고 답변 처리 메서드
     *
     * 동작 순서:
     * 1. 답변 내용 검증
     * 2. 답변 저장
     * 3. 상태를 RESOLVED로 변경
     * 4. 최초 처리이면 처리 일시 저장
     * 5. 답변 수정 일시 갱신
     * 6. 처리 관리자 번호 저장
     */
    public void answerReport(String reportAnswer, Long adminNo) {
        if (this.reportAnswer != null && !this.reportAnswer.trim().isEmpty()) {
            throw new IllegalStateException("신고 답변은 한 번만 작성할 수 있습니다.");
        }

        if (reportAnswer == null || reportAnswer.trim().isEmpty()) {
            throw new IllegalArgumentException("처리 답변은 비어 있을 수 없습니다.");
        }

        this.reportAnswer = reportAnswer.trim();
        this.reportStatus = "RESOLVED";
        this.processedAdminNo = adminNo;

        if (this.reportProcessedAt == null) {
            this.reportProcessedAt = LocalDateTime.now();
        }

        this.reportAnswerUpdatedAt = LocalDateTime.now();
    }
}