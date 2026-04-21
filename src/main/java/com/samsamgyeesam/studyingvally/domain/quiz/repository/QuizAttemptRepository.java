package com.samsamgyeesam.studyingvally.domain.quiz.repository;

import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    // 파라미터 String quizNo -> Long quizNo 변경
    Optional<QuizAttempt> findByQuizNoAndUserNo(Long quizNo, Long userNo);

    List<QuizAttempt> findByUserNo(Long userNo);
}