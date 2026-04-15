package com.samsamgyeesam.studyingvally.domain.quiz.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuizDTO {
    private int quizNo;
    private String quizTitle;
    private int chapNo; // 파라미터로 받을 챕터 번호
}