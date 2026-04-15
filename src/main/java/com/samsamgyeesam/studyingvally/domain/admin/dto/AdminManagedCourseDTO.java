package com.samsamgyeesam.studyingvally.domain.admin.dto;

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

    /* 원본 값 */
    private String courseStatus;

    /* 화면 출력용 한글 값 */
    private String courseStatusLabel;
}