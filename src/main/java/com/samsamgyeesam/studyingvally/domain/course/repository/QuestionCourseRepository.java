package com.samsamgyeesam.studyingvally.domain.course.repository;

import com.samsamgyeesam.studyingvally.domain.course.entity.QuestionCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionCourseRepository extends JpaRepository<QuestionCourse,Long> {

//    // 강의별 질문 조회
//    List<QuestionCourse> findByCourseIdOrderByQuestionCourseNoDesc(Long courseId);

    // 강의 삭제 시 질문도 삭제
    void deleteByCourseId(Long courseId);

    // 특정 강의의 질문 조회
    @Query("SELECT q FROM QuestionCourse q JOIN FETCH q.course WHERE q.courseId = :courseId ORDER BY q.questionCourseNo DESC")
    List<QuestionCourse> findByCourseIdWithCourse(@Param("courseId") Long courseId);

}
