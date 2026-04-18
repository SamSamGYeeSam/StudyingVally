package com.samsamgyeesam.studyingvally.domain.study.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseNoticeDTO {
    private Long courseNoticeNo;      // cn.courseNoticeNo
    private String createdAt;         // CAST(cn.createdAt AS string)
    private String courseTitle;       // c.courseTitle
    private String userName;          // u.userName
    private String courseNoticeTitle; // cn.courseNoticeTitle
    private String courseNoticeDesc;

}
