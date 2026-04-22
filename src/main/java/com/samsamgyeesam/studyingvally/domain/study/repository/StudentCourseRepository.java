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

        List<StudentCourse> findByCourseStatusIgnoreCaseOrderByCourseCreatedAtDesc(String courseStatus);
        @Query("SELECT new com.samsamgyeesam.studyingvally.domain.study.dto.StudentCourseNoticeDTO(" +
                "cn.courseNoticeNo, " +
                "str(cn.createdDate), " +
                "c.courseTitle, " +
                "COALESCE(u.userNickname, '알 수 없음'), " +
                "cn.courseNoticeTitle, " +
                "cn.courseNoticeDesc) " +
                "FROM StudentCourseNotice cn " +
                "JOIN cn.course c " +
                "LEFT JOIN cn.user u " +
                "WHERE c.courseId IN :courseIds " +
                "AND c.courseStatus = 'OPEN' " +
                "ORDER BY cn.createdDate DESC")
        List<StudentCourseNoticeDTO> findNoticesByCourseIds(@Param("courseIds") List<Long> courseIds);
}