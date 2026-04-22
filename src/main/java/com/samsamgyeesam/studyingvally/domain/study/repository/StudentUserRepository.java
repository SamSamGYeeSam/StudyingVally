package com.samsamgyeesam.studyingvally.domain.study.repository;

import com.samsamgyeesam.studyingvally.domain.study.entity.StudentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentUserRepository extends JpaRepository<StudentUser, Long> {
    Optional<StudentUser> findByUserId(String userId);

    @Query(value = "SELECT u.user_gender FROM user u " +
            "JOIN course c ON c.user_no = u.user_no " +
            "WHERE c.course_id = :courseId",
            nativeQuery = true)
    String findInstructorGenderByCourseId(@Param("courseId") Long courseId);
}
