package com.samsamgyeesam.studyingvally.domain.course.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.samsamgyeesam.studyingvally.baseentity.BaseTimeEntity;
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
@Table(name = "evaluations")
public class Evaluation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_no")
    private Long evaluationNo;

    @Column(name = "evaluation_score", nullable = false)
    private Long evaluationScore;

    @Column(name = "evaluation_desc", nullable = false)
    private String evaluationDesc;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "user_no")
    private Long userNo;
}
