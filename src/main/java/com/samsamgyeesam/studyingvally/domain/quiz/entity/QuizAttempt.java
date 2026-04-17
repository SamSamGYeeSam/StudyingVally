package com.samsamgyeesam.studyingvally.domain.quiz.entity;

import com.samsamgyeesam.studyingvally.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
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
    private Integer quizScore; // 맞힌 문제 수 (점수)

    @Column(name = "quiz_result")
    private String quizResult; // "CLEAR" 또는 "GAME_OVER"

    @Column(name = "quiz_no")
    private String quizNo;

    @Column(name = "user_no")
    private Long userNo;
}