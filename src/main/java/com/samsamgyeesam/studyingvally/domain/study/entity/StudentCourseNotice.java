package com.samsamgyeesam.studyingvally.domain.study.entity;

import com.samsamgyeesam.studyingvally.domain.course.entity.Course;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "course_notice")
public class StudentCourseNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_notice_no")
    private Long courseNoticeNo;

    @Column(name = "course_notice_title")
    private String courseNoticeTitle;

    @Column(name = "course_notice_desc")
    private String courseNoticeDesc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course; // 강의명 추출용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private StudentUser user; // 작성자(강사) 정보

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
