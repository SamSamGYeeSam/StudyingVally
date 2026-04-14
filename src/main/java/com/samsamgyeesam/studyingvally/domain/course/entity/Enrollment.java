package com.samsamgyeesam.studyingvally.domain.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_no")
    private Long enrollmentNo;

    @Column(name = "enrollment_process", nullable = false)
    private Double enrollmentProcess;

    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "course_id", nullable = false)
    private Long courseId;
}
