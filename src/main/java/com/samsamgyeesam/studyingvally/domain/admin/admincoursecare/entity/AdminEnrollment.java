package com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* comment.
 * 관리자 사용자 상세 조회에서 수강 정보를 조회하기 위한 엔티티 클래스
 */

@Entity
@Table(name = "enrollment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminEnrollment {

    @Id
    @Column(name = "enrollment_no")
    private Long enrollmentNo;

    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "course_id", nullable = false)
    private Long courseId;
}