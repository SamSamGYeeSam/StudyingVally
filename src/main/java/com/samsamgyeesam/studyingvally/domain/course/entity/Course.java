package com.samsamgyeesam.studyingvally.domain.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(name = "course_title")
    private String courseTitle;

    @Column(name = "course_description")
    private String courseDescription;

    @Column(name = "course_created_at")
    private LocalDateTime courseCreatedAt;

    @Column(name = "course_status")
    private String courseStatus;

    @Column(name = "course_send_approve")
    private Integer courseSendApprove;

    @Column(name = "user_no")
    private Long userNo;

    public Course(String courseTitle, String courseDescription,
                  String courseStatus, Integer courseSendApprove, Long userNo) {
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseStatus = courseStatus;
        this.courseSendApprove = courseSendApprove;
        this.userNo = userNo;
        this.courseCreatedAt = LocalDateTime.now();
    }

//
//    public Course changeCourseTitle(String newTitle) {
//        this.courseTitle = newTitle;
//        return this;
//    }
//
//    public Course changeCourseDescription(String newDescription) {
//        this.courseDescription = newDescription;
//        return this;
//    }
//
//    public Course changeCourseStatus(String newStatus) {
//        this.courseStatus = newStatus;
//        return this;
//    }
//
//    public Course builder() {
//        return new Course(courseId, courseTitle, courseDescription,
//                courseCreatedAt, courseStatus, courseSendApprove, userNo);
//    }

}
