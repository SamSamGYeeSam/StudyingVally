package com.samsamgyeesam.studyingvally.domain.notice.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeacherNoticeDTO {

    private Long noticeNo;
    private String noticeTitle;
    private String noticeDesc;

    private LocalDateTime createdDate;

}
