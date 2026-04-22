package com.samsamgyeesam.studyingvally.domain.quiz.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuizListDTO {
    private Long quizListNo;
    private String quizTitle;
    private String quizDesc;
    private String quizAnswer;
    private String quizAnswerDesc;
    private Long quizScore;
    private Long quizNo; // String -> Long 변경
}