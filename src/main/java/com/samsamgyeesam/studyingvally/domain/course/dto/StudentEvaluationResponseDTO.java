package com.samsamgyeesam.studyingvally.domain.course.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentEvaluationResponseDTO {

    private Double score;
    private String content;
    private String nickname;

    public StudentEvaluationResponseDTO(String content, Double score, String nickname) {
        this.content = content;
        this.score = score;
        this.nickname = nickname;
    }

}
