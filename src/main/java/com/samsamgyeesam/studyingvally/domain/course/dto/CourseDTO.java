package com.samsamgyeesam.studyingvally.domain.course.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CourseDTO {

    private String courseTitle;
    private String courseDescription;

    private Long courseId;
    private LocalDateTime courseCreatedAt;
    private String courseStatus;            // 강의 오픈 여부
    private Integer courseSendApprove;      // 승인요청여부
    private Long userNo;

}
