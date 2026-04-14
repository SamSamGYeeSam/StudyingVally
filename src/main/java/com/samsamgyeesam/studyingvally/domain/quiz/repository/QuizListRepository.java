package com.samsamgyeesam.studyingvally.domain.quiz.repository;

import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizListRepository extends JpaRepository<QuizList, Integer> {

}
