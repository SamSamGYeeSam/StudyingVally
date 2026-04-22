package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminNoticeUpdateRequestDTO {

    private Long noticeNo;
    private String noticeTitle;
    private String noticeDesc;
}