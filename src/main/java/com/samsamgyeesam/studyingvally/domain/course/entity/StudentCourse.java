package com.samsamgyeesam.studyingvally.domain.course.entity;

import com.samsamgyeesam.studyingvally.domain.user.entity.StudentUser;
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
    private Long courseId; // 강의번호

    @Column(name = "course_title", nullable = false)
    private String courseTitle; // 강의제목

    @Column(name = "course_description", nullable = false, columnDefinition = "TEXT")
    private String courseDescription; // 강의 설명

    @Column(name = "course_created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime courseCreatedAt; // 개설시간

    @Column(name = "course_status", nullable = false, length = 20)
    private String courseStatus; // 강의 오픈 여부 (OPEN, CLOSED)

    @Column(name = "course_send_approve", nullable = false)
    private Integer courseSendApprove; // 승인요청여부 (0: 미요청, 1: 요청)

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
