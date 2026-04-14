package com.samsamgyeesam.studyingvally.domain.course.repository;

import com.samsamgyeesam.studyingvally.domain.course.entity.StudentReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentReviewRepository extends JpaRepository<StudentReview, Long> {

    @Query("SELECT r FROM StudentReview r JOIN FETCH r.user WHERE r.courseId = :courseId")
    List<StudentReview> findByCourseId(@Param("courseId") Long courseId);

}
