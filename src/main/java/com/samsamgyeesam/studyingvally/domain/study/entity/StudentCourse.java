package com.samsamgyeesam.studyingvally.domain.study.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table (name = "course")
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_title", nullable = false)
    private String courseTitle;

    @Column(name = "course_description", nullable = false, columnDefinition = "TEXT")
    private String courseDescription;

    @Column(name = "course_created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime courseCreatedAt;

    @Column(name = "course_status", nullable = false, length = 20)
    private String courseStatus;

    @Column(name = "course_send_approve", nullable = false)
    private Integer courseSendApprove;

    @Column(name = "user_no")
    private Long userNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private StudentUser user;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<StudentEnrollment> studentEnrollments = new ArrayList<>();

    @OneToMany(mappedBy = "studentCourse", cascade = CascadeType.ALL)
    private List<StudentEvaluation> studentEvaluations = new ArrayList<>();

}
