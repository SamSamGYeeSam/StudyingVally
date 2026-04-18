package com.samsamgyeesam.studyingvally.domain.study.repository;

import com.samsamgyeesam.studyingvally.domain.study.entity.StudentChapterAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentChapterAttemptRepository extends JpaRepository<StudentChapterAttempt, Long> {

    @Query("SELECT COUNT(a) FROM StudentChapterAttempt a WHERE a.userNo = :userNo " +
            "AND a.chapNo IN (SELECT c.chapNo FROM StudentChapter c WHERE c.courseId = :courseId)")
    long countCompletedChapters(@Param("userNo") Long userNo, @Param("courseId") Long courseId);

    @Query("SELECT a.chapNo FROM StudentChapterAttempt a " +
            "WHERE a.userNo = :userNo " +
            "AND a.chapNo IN (SELECT c.chapNo FROM StudentChapter c WHERE c.courseId = :courseId)")
    List<Long> findCompletedChapNosByUser(@Param("userNo") Long userNo, @Param("courseId") Long courseId);

    @Query("SELECT a.chapNo FROM StudentChapterAttempt a " +
            "WHERE a.userNo = :userNo " +
            "AND a.chapNo IN (SELECT c.chapNo FROM StudentChapter c WHERE c.courseId = :courseId)")
    List<Long> findCompletedChapterNos(@Param("userNo") Long userNo, @Param("courseId") Long courseId);

    boolean existsByUserNoAndChapNo(Long userNo, Long chapNo);
}
