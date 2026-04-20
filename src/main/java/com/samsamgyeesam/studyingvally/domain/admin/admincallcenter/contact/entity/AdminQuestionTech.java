package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.entity;

import com.samsamgyeesam.studyingvally.baseentity.BaseOnlyCreateTime;
import com.samsamgyeesam.studyingvally.domain.admin.adminusercare.entity.AdminUser;
import com.samsamgyeesam.studyingvally.domain.admin.exception.AdminException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "question_tech")
@Getter
@NoArgsConstructor
public class AdminQuestionTech extends BaseOnlyCreateTime {

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

    @Column(name = "question_answered_at")
    private LocalDateTime questionAnsweredAt;

    public void answerQuestion(String questionAnswer) {
        if (this.questionAnswer != null && !this.questionAnswer.trim().isEmpty()) {
            throw new AdminException("이미 답변이 완료된 문의입니다.");
        }

        if (questionAnswer == null || questionAnswer.trim().isEmpty()) {
            throw new AdminException("답변 내용을 입력해 주세요.");
        }

        this.questionAnswer = questionAnswer.trim();
        this.questionStatus = "RESOLVED";
        this.questionAnsweredAt = LocalDateTime.now();
    }
}