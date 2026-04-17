package com.samsamgyeesam.studyingvally.domain.admin.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class AdminNoticeDetailDTO {

    private Long noticeNo;
    private String noticeTitle;
    private String noticeDesc;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}