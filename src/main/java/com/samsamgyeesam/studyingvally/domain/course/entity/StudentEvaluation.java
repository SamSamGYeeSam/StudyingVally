package com.samsamgyeesam.studyingvally.domain.course.entity;

import com.samsamgyeesam.studyingvally.domain.user.entity.StudentUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "course_id")
    private Long courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private StudentCourse studentCourse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private StudentUser user;
}
