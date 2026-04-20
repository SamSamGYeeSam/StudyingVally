package com.samsamgyeesam.studyingvally.domain.study.repository;

import com.samsamgyeesam.studyingvally.domain.study.dto.StudentCourseNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentCourse;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentCourseNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {

        List<StudentCourse> findByCourseStatusIgnoreCase(String courseStatus);

        @Query("SELECT new com.samsamgyeesam.studyingvally.domain.study.dto.StudentCourseNoticeDTO(" +
                "cn.courseNoticeNo, " +
                "CAST(cn.createdAt AS string), " +
                "c.courseTitle, " +
                "u.userName, " +
                "cn.courseNoticeTitle, " +
                "cn.courseNoticeDesc) " +
                "FROM StudentCourseNotice cn " +
                "JOIN cn.course c " +
                "JOIN cn.user u " +
                "WHERE c.courseId IN :courseIds " +
                "AND c.courseStatus = 'OPEN'")
        List<StudentCourseNoticeDTO> findNoticesByCourseIds(@Param("courseIds") List<Long> courseIds);
}