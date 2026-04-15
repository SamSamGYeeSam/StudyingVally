package com.samsamgyeesam.studyingvally.domain.quiz.repository;

import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizEnrollment;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizEnrolledCourseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizEnrollmentRepository extends JpaRepository<QuizEnrollment, Long> {

    @Query("SELECT new com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizEnrolledCourseDTO(c.courseId, c.courseTitle) " +
            "FROM QuizEnrollment e, QuizCourse c " +
            "WHERE e.courseId = c.courseId AND e.userNo = :userNo")
    List<QuizEnrolledCourseDTO> findEnrolledCoursesByUserNo(@Param("userNo") Long userNo);
}