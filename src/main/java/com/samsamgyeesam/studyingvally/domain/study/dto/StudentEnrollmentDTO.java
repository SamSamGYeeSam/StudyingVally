package com.samsamgyeesam.studyingvally.domain.study.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentEnrollmentDTO {

    private Long enrollmentNo;
    private Long enrollmentProcess;
    private Long userNo;
    private Long courseId;
    private Long createDate;

}
