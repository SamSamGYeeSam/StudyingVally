package com.samsamgyeesam.studyingvally.domain.notice.entity;

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
@Table(name = "course_notice")
public class TeacherCourseNotice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_notice_no")
    private Long courseNoticeNo;

    @Column(name = "course_notice_title", nullable = false)
    private String courseNoticeTitle;

    @Column(name = "course_notice_desc", nullable = false)
    private String courseNoticeDesc;

    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "course_id")
    private Long courseId;
}
