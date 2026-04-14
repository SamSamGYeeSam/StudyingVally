package com.samsamgyeesam.studyingvally.domain.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class CourseForQuiz {
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private int courseId;
    private String courseTitle;
    private String course_description;
    private Date courseCreatedAt;
    private boolean course_status;
}
