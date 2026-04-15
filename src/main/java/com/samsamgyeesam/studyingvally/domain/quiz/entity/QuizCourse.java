package com.samsamgyeesam.studyingvally.domain.quiz.entity;

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
@Table(name = "course")
public class QuizCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_title")
    private String courseTitle;

    // 추가적인 강의 정보가 있다면 아래에 계속 선언해 주시면 됩니다.
    @Column(name = "course_desc")
    private String courseDesc;

}