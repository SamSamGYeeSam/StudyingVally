package com.samsamgyeesam.studyingvally.domain.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "question_tech")
@Getter
@NoArgsConstructor
public class AdminQuestionTech {

    @Id
    @Column(name = "question_tech_no")
    private Long questionTechNo;

    @Column(name = "question_title", nullable = false)
    private String questionTitle;

    @Column(name = "question_desc", nullable = false)
    private String questionDesc;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "user_no")
    private Long userNo;

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
}