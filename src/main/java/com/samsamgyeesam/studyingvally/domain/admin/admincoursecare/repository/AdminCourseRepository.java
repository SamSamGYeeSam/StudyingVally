package com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.repository;

import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.entity.AdminCourse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;



public interface AdminCourseRepository extends JpaRepository<AdminCourse, Long> {


    @EntityGraph(attributePaths = "teacher")
    List<AdminCourse> findByTeacher_UserNoOrderByCourseIdDesc(Long userNo);


    @EntityGraph(attributePaths = "teacher")
    List<AdminCourse> findByCourseIdInOrderByCourseIdDesc(List<Long> courseIds);


    @EntityGraph(attributePaths = "teacher")
    List<AdminCourse> findByCourseSendApproveTrueOrderByCourseIdDesc();


    @EntityGraph(attributePaths = "teacher")
    List<AdminCourse> findByCourseSendApproveTrueAndCourseStatusOrderByCourseIdDesc(String courseStatus);


    @EntityGraph(attributePaths = "teacher")
    Optional<AdminCourse> findDetailByCourseId(Long courseId);
}