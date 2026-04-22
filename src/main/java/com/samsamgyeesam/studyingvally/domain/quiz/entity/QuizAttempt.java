package com.samsamgyeesam.studyingvally.domain.quiz.entity;

import com.samsamgyeesam.studyingvally.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "quiz_attempt")
public class QuizAttempt extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_attempt_id")
    private Long quizAttemptId;

    @Column(name = "quiz_score")
    private Integer quizScore;

    @Column(name = "quiz_result")
    private String quizResult;

    @Column(name = "quiz_no")
    private Long quizNo;

    @Column(name = "user_no")
    private Long userNo;
}