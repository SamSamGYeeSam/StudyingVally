package com.samsamgyeesam.studyingvally.domain.quiz.repository;

import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<QuizQuiz, Long> {

    List<QuizQuiz> findByChapNo(Long chapNo);

}