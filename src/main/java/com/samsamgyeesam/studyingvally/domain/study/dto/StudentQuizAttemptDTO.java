package com.samsamgyeesam.studyingvally.domain.study.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentQuizAttemptDTO {

    private Long attemptId;
    private Long quizId;
    private Long userNo;
    private Integer score;
    private Boolean isPassed;
    private LocalDateTime attemptDate;

}
