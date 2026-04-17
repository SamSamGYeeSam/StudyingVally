package com.samsamgyeesam.studyingvally.domain.admin.dto.notice;

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