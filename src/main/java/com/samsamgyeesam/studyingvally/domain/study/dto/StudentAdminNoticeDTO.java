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
    private String formattedDate;
    private String title;
    private String desc;
}
