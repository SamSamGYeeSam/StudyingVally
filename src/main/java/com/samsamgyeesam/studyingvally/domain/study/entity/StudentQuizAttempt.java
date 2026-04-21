package com.samsamgyeesam.studyingvally.domain.study.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_attempt")
public class StudentQuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_attempt_id")
    private Long attemptId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_no")
    private StudentQuiz quiz;

    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "quiz_score")
    private Integer score;

    @Column(name = "created_at")
    private LocalDateTime attemptDate;

}

