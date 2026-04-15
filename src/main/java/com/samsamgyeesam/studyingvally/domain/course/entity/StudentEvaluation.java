package com.samsamgyeesam.studyingvally.domain.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "evaluations")
public class StudentEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_no")
    private Long evaluationNo;

    @Column(name = "evaluation_score")
    private Double evaluationScore;

    @Column(name = "evaluation_desc", columnDefinition = "TEXT")
    private String evaluationDesc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private StudentCourse course;

    @Column(name = "user_no")
    private Long userNo;

}
