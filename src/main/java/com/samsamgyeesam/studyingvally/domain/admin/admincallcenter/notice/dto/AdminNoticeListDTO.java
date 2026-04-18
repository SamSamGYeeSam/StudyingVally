package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminNoticeListDTO {

    private Long noticeNo;
    private String noticeTitle;
    private LocalDateTime createdAt;
}