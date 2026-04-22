package com.samsamgyeesam.studyingvally.domain.study.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "question_course")
public class StudentCourseQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_course_no")
    private Long questionCourseNo;

    @Column(name = "question_course_title", nullable = false)
    private String questionCourseTitle;

    @Column(name = "question_course_desc", nullable = false, columnDefinition = "TEXT")
    private String questionCourseDesc;

    @Column(name = "question_course_answer", columnDefinition = "TEXT")
    private String questionCourseAnswer;

    @Column(name = "user_no", nullable = false)
    private Long userNo;

    @Column(name = "course_id")
    private Long courseId;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }

}
