package com.samsamgyeesam.studyingvally.domain.course.entity;

import com.samsamgyeesam.studyingvally.domain.user.entity.UserUser;
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
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_no")
    private Long enrollmentNo;

    @Column(name = "enrollment_process", nullable = false)
    private Double enrollmentProcess;

    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    // 수강생이랑 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private UserUser user;
}
