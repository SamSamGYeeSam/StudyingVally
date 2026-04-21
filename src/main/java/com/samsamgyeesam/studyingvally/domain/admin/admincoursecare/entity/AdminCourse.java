package com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.entity;

import com.samsamgyeesam.studyingvally.domain.admin.adminusercare.entity.AdminUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "course")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminCourse {

    @Id
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_title", nullable = false)
    private String courseTitle;

    @Column(name = "course_description", nullable = false)
    private String courseDescription;

    @Column(name = "course_created_at", nullable = false)
    private LocalDateTime courseCreatedAt;

    @Column(name = "course_status", nullable = false)
    private String courseStatus;

    @Column(name = "course_send_approve", nullable = false)
    private Boolean courseSendApprove;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private AdminUser teacher;

    public void changeCourseStatus(String courseStatus) {

        this.courseStatus = courseStatus;
    }
}