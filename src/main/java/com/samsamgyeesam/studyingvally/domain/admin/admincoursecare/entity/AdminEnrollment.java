package com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enrollment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminEnrollment {

    @Id
    @Column(name = "enrollment_no")
    private Long enrollmentNo;

    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "course_id", nullable = false)
    private Long courseId;
}