package com.samsamgyeesam.studyingvally.domain.study.repository;

import com.samsamgyeesam.studyingvally.domain.study.entity.StudentEvaluation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface
StudentEvaluationRepository extends JpaRepository<StudentEvaluation, Long> {
    boolean existsByUser_UserNoAndStudentCourse_CourseId(Long userNo, Long courseId);
    List<StudentEvaluation> findByStudentCourse_CourseId(Long courseId);
    Optional<StudentEvaluation> findByUser_UserNoAndStudentCourse_CourseId(Long userNo, Long courseId);
}
