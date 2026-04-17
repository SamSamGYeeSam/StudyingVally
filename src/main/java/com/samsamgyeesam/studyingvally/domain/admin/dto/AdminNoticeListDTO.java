package com.samsamgyeesam.studyingvally.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminNoticeListDTO {

    private Integer displayNo;
    private Long noticeNo;
    private String noticeTitle;
    private String noticeDesc;
}