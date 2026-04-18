package com.samsamgyeesam.studyingvally.domain.study.repository;

import com.samsamgyeesam.studyingvally.domain.study.entity.StudentEvaluation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface
StudentEvaluationRepository extends JpaRepository<StudentEvaluation, Long> {
    List<StudentEvaluation> findByStudentCourse_CourseId(Long courseId);
}
