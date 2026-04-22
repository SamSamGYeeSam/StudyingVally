package com.samsamgyeesam.studyingvally.domain.study.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentQuizDTO {

    private Long quizId;
    private String quizTitle;
    private Integer quizOrder;
    private Integer totalQuestions;
    private String status;
    private Integer lastScore;

}
