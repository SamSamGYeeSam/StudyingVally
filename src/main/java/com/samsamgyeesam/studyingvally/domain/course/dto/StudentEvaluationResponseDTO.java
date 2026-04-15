package com.samsamgyeesam.studyingvally.domain.course.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentEvaluationResponseDTO {

    private Double score;
    private String content;
    private String nickname;

}
