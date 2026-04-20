package com.samsamgyeesam.studyingvally.domain.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttemptDTO {
    private Long quizScore;
    private String quizResult;
    private String quizNo;
    private Long userNo;
}