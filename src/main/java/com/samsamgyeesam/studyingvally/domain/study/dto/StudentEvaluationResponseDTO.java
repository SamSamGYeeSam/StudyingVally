package com.samsamgyeesam.studyingvally.domain.study.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentEvaluationResponseDTO {

    private Long courseId;
    private String courseName;
    private int progress;
    private boolean hasEvaluation;
    private Double score;
    private String content;
    private String nickname;

}
