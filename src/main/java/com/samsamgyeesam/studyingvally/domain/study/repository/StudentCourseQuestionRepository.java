package com.samsamgyeesam.studyingvally.domain.study.repository;

import com.samsamgyeesam.studyingvally.domain.study.entity.StudentCourseQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentCourseQuestionRepository extends JpaRepository<StudentCourseQuestion, Long> {
//    List<StudentCourseQuestion> findByUserNoOrderByQuestionCourseNoDesc(Long userNo);
    List<StudentCourseQuestion> findByUserNoAndCourseIdOrderByQuestionCourseNoDesc(Long userNo, Long courseId);
}
