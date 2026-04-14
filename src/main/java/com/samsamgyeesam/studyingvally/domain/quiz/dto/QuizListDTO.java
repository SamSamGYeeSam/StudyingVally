package com.samsamgyeesam.studyingvally.domain.quiz.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class QuizListDTO {
    private int quizListNo;
    private String quizTitle;
    private String quizDesc;
    private String quizAnswer;
    private String quizAnswerDesc;
    private String quizNo;
}
