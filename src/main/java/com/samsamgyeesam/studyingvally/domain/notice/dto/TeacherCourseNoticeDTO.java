package com.samsamgyeesam.studyingvally.domain.notice.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeacherCourseNoticeDTO {

    private Long courseNoticeNo;
    private String courseNoticeTitle;
    private String courseNoticeDesc;
    private Long userNo;
    private Long courseId;

    private LocalDateTime createdDate;

    private String courseName;
}
