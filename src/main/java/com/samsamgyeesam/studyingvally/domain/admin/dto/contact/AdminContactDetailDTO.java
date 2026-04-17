package com.samsamgyeesam.studyingvally.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class AdminContactDetailDTO {

    private Long questionTechNo;
    private String questionTitle;
    private String questionDesc;
    private String userName;
    private String userNickname;
    private String questionStatusLabel;
    private String questionAnswer;
    private LocalDateTime questionAnsweredAt;
    private LocalDateTime questionAnswerUpdatedAt;
}