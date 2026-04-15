package com.samsamgyeesam.studyingvally.domain.quiz.repository;

import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
}