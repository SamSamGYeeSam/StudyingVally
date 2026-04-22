package com.samsamgyeesam.studyingvally.domain.notice.entity;

import com.samsamgyeesam.studyingvally.baseentity.BaseTimeEntity;
import com.samsamgyeesam.studyingvally.domain.course.entity.Course;
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

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "user_no")
    private Long userNo;

    // 강의랑 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;

    // 등록용 생성자
    public TeacherCourseNotice(String courseNoticeTitle, String courseNoticeDesc, Long courseId, Long userNo) {
        this.courseNoticeTitle = courseNoticeTitle;
        this.courseNoticeDesc = courseNoticeDesc;
        this.courseId = courseId;
        this.userNo = userNo;
    }

    // 강의소식 수정 메서드
    public void updateCourseNoticeInfo(String courseNoticeTitle, String courseNoticeDesc) {
        this.courseNoticeTitle = courseNoticeTitle;
        this.courseNoticeDesc = courseNoticeDesc;
    }
}
