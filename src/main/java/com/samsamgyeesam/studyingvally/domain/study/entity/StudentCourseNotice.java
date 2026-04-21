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
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private StudentUser user;

    @Column(name = "user_name")
    private String userName;

    @Column(updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}
