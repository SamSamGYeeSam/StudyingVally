package com.samsamgyeesam.studyingvally.domain.quiz.repository;

import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizQuizList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizListRepository extends JpaRepository<QuizQuizList, Long> {

    List<QuizQuizList> findByQuizNo(String quizNo);

}
