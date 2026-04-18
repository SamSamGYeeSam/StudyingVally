package com.samsamgyeesam.studyingvally.domain.course.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuestionCourseDTO {

    private Long questionCourseNo;
    private String questionCourseTitle;
    private String questionCourseDesc;
    private Long userNo;
    private Long courseId;
    private String questionCourseAnswer;

    private LocalDateTime createdDate;

    private String courseTitle;
    private String userName;
    private String userNickname;

}
