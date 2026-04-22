package com.samsamgyeesam.studyingvally.domain.course.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EnrollmentDTO {

    private Long enrollmentNo;
    private Double enrollmentProcess;
    private Long userNo;
    private Long courseId;

    private String userName;
    private String userNickname;
}
