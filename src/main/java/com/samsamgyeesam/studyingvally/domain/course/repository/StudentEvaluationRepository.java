package com.samsamgyeesam.studyingvally.domain.course.repository;

import com.samsamgyeesam.studyingvally.domain.course.entity.StudentEvaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentEvaluationRepository extends JpaRepository<StudentEvaluation, Long> {
    List<StudentEvaluation> findByStudentCourse_CourseId(Long courseId);
}
