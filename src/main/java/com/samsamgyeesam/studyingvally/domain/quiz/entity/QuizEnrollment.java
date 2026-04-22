package com.samsamgyeesam.studyingvally.domain.quiz.entity;

import com.samsamgyeesam.studyingvally.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "enrollment")
public class QuizEnrollment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_no")
    private Long enrollmentNo;

    @Column(name = "enrollment_process")
    private String enrollmentProcess;

    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "course_id")
    private Long courseId;

}