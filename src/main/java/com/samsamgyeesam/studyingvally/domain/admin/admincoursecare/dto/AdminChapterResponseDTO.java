package com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminChapterResponseDTO {

    private Long chapNo;
    private String chapTitle;
    private String chapDesc;
    private String chapUrl;
}