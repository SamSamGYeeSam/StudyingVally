package com.samsamgyeesam.studyingvally.domain.quiz.repository;

import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    //특정 퀴즈(String)를 특정 유저(Long)가 푼 적이 있는지 확인 (Upsert 로직용)
    Optional<QuizAttempt> findByQuizNoAndUserNo(String quizNo, Long userNo);

    //특정 유저(Long)가 푼 모든 퀴즈 기록 가져오기 (퀴즈 목록의 점수표 출력용)
    List<QuizAttempt> findByUserNo(Long userNo);
}