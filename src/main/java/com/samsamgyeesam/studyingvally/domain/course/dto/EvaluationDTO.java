package com.samsamgyeesam.studyingvally.domain.course.dto;

import lombok.*;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EvaluationDTO {

    private Long evaluationNo;
    private Long evaluationScore;
    private String evaluationDesc;
    private Long courseId;
    private Long userNo;

    private String userName;
    private String userNickname;

    private LocalDateTime createdDate;

}
