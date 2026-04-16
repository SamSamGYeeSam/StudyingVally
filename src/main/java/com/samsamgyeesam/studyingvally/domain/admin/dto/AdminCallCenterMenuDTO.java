package com.samsamgyeesam.studyingvally.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminCallCenterMenuDTO {

    private Integer menuNo;
    private String title;
    private String description;
    private String moveUrl;
}