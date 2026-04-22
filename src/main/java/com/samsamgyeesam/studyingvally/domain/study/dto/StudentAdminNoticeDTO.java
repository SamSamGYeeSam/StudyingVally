package com.samsamgyeesam.studyingvally.domain.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentAdminNoticeDTO {
    private Long noticeNo;
    private String createdDate;
    private String noticeTitle;
    private String noticeDesc;
}
