package com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/* comment.
 * 관리자 강의 목록 화면에 전달할 DTO 클래스
 */

@Getter
@AllArgsConstructor
public class AdminManagedCourseDTO {

    private Long courseId;
    private String courseTitle;
    private String courseDescription;
    private String courseCreatedAt;
    private String teacherName;
    private String courseStatus;
    private String courseStatusLabel;
}