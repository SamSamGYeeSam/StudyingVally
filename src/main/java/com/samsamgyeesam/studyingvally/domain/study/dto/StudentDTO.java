package com.samsamgyeesam.studyingvally.domain.study.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class StudentDTO {

    private Long userNo;
    private String userId;
    private String userPassword;
    private String userPhoneNumber;
    private String userEmail;
    private String userRole;
    private String userNickname;
    private String userName;
    private String userStatus;
    private String userGender;

    private List<EnrolledCourseDTO> enrolledCourses;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EnrolledCourseDTO {
        private Long courseId;
        private String courseTitle;
        private String targetUrl;
        private int progress;
        private boolean hasEvaluation;
        private String score;
        private String userGender;
        private String courseStatus;
    }
}