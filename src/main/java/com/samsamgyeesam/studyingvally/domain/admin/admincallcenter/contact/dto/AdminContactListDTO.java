package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminContactListDTO {

    private Long questionTechNo;
    private String questionTitle;
    private String userName;
    private String userNickname;
    private String questionStatusLabel;
    private LocalDateTime questionAnsweredAt;
}