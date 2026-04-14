package com.samsamgyeesam.studyingvally.domain.course.repository;

import com.samsamgyeesam.studyingvally.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // 사용자 번호(강사 번호)로 강의 조회
    List<Course> findByUserNoOrderByCourseCreatedAtDesc(Long userNo);
}
