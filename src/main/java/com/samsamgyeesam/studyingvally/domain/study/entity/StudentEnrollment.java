package com.samsamgyeesam.studyingvally.domain.study.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "enrollment")
public class StudentEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_no")
    private Long enrollmentNo;

    @Column(name = "enrollment_process")
    private Double enrollmentProcess;

    @Column(name = "user_no")
    private Long userNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private StudentCourse course;

//    @Column(name = "enrollment_progress")
//    private int enrollmentProgress;

}
