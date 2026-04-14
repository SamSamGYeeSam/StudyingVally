package com.samsamgyeesam.studyingvally.domain.course.repository;

import com.samsamgyeesam.studyingvally.domain.course.entity.StudentEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentEvaluationRepository extends JpaRepository<StudentEvaluation, Long> {
    List<StudentEvaluation> findByCourse_CourseId(Long courseId);
}
