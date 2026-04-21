package com.samsamgyeesam.studyingvally.domain.study.repository;

import com.samsamgyeesam.studyingvally.domain.study.entity.StudentQuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentQuizAttemptRepository extends JpaRepository<StudentQuizAttempt, Long> {
    Optional<StudentQuizAttempt> findTopByUserNoAndQuiz_Chapter_Course_CourseIdOrderByAttemptIdDesc(Long userNo, Long courseId);
}
