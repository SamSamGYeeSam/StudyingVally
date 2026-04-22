package com.samsamgyeesam.studyingvally.domain.study.repository;

import com.samsamgyeesam.studyingvally.domain.study.entity.StudentEnrollment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {
    @Query("SELECT e FROM StudentEnrollment e JOIN e.course c " +
            "WHERE e.userNo = :userNo AND c.courseStatus = 'OPEN'" +
            "ORDER BY e.enrollmentNo DESC")
    List<StudentEnrollment> findByUserNo(Long userNo);

    Optional<StudentEnrollment> findByUserNoAndCourse_CourseId(Long userNo, Long courseId);

    @Modifying
    @Transactional
    @Query("UPDATE StudentEnrollment e SET e.enrollmentProcess = :progress " +
            "WHERE e.userNo = :userNo AND e.course.courseId = :courseId")
    void updateProgress(@Param("userNo") Long userNo,
                        @Param("courseId") Long courseId,
                        @Param("progress") double progress);
}


