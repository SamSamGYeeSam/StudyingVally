package com.samsamgyeesam.studyingvally.domain.course.entity;

import com.samsamgyeesam.studyingvally.domain.user.entity.StudentUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "evaluations")
@Getter
@Setter
public class StudentReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_no")
    private Long reviewId;

    @Column(name = "evaluation_desc")
    private String content;

    @Column(name = "evaluation_score")
    private Double score;

    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "course_id")
    private Long courseId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private StudentUser user;

}
