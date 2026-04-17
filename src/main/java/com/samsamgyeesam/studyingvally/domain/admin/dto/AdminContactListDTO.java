package com.samsamgyeesam.studyingvally.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminContactListDTO {

    private Integer displayNo;
    private Long questionTechNo;
    private String questionTitle;
    private String questionDesc;
    private Long courseId;
    private Long userNo;
    private String questionStatus;
    private String questionAnswer;
    private Long answeredAdminNo;
    private LocalDateTime questionAnsweredAt;
    private LocalDateTime questionAnswerUpdatedAt;
}