package com.samsamgyeesam.studyingvally.domain.admin.entity;

import com.samsamgyeesam.studyingvally.baseentity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/* comment.
 * 관리자 문의함 기능에서 question_tech 테이블을 조회/수정하기 위한 엔티티 클래스
 *
 * 왜 필요한가:
 * - 문의 목록 조회
 * - 문의 상세 조회
 * - 답변 작성
 * - 처리 상태 변경
 *
 * 주의할 점:
 * - userNo 숫자만 두면 이름/닉네임을 바로 꺼낼 수 없으므로
 *   AdminUser와 연관관계로 연결한다.
 * - 엔티티 수정은 setter 대신 내부 메서드로 처리한다.
 */
@Entity
@Table(name = "question_tech")
@Getter
@NoArgsConstructor
public class AdminQuestionTech extends BaseTimeEntity {

    /* comment.
     * 문의 PK
     *
     * 주의:
     * - question_tech_no가 AUTO_INCREMENT라면 GeneratedValue를 사용해야 한다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_tech_no")
    private Long questionTechNo;

    @Column(name = "question_title", nullable = false)
    private String questionTitle;

    @Column(name = "question_desc", nullable = false)
    private String questionDesc;

    @Column(name = "course_id")
    private Long courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private AdminUser user;

    @Column(name = "question_status", nullable = false)
    private String questionStatus;

    @Column(name = "question_answer")
    private String questionAnswer;

    @Column(name = "answered_admin_no")
    private Long answeredAdminNo;

    @Column(name = "question_answered_at")
    private LocalDateTime questionAnsweredAt;

    @Column(name = "question_answer_updated_at")
    private LocalDateTime questionAnswerUpdatedAt;

    public void answerQuestion(String questionAnswer, Long adminNo) {
        if (this.questionAnswer != null && !this.questionAnswer.trim().isEmpty()) {
            throw new IllegalStateException("문의 답변은 한 번만 작성할 수 있습니다.");
        }

        if (questionAnswer == null || questionAnswer.trim().isEmpty()) {
            throw new IllegalArgumentException("답변 내용은 비어 있을 수 없습니다.");
        }

        this.questionAnswer = questionAnswer.trim();
        this.questionStatus = "RESOLVED";
        this.answeredAdminNo = adminNo;

        if (this.questionAnsweredAt == null) {
            this.questionAnsweredAt = LocalDateTime.now();
        }

        this.questionAnswerUpdatedAt = LocalDateTime.now();
    }
}