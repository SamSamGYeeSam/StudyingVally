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
    private Integer quizScore;
    private String quizResult;
    private Long quizNo; // String -> Long 변경
    private Long userNo;
}