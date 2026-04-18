package com.samsamgyeesam.studyingvally.domain.study.repository;

import com.samsamgyeesam.studyingvally.domain.study.dto.StudentCourseNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentCourseNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourseNotice, Long> {

        @Query("SELECT new com.samsamgyeesam.studyingvally.domain.course.dto.StudentCourseNoticeDTO(" +
                "cn.courseNoticeNo, " +
                "CAST(cn.createdAt AS string), " +
                "c.courseTitle, " +
                "u.userName, " + // 중요: StudentUser 엔티티 안에 'userName' 필드가 있어야 합니다. 아니라면 'name' 등으로 수정하세요.
                "cn.courseNoticeTitle, " +
                "cn.courseNoticeDesc) " +
                "FROM StudentCourseNotice cn " +
                "JOIN cn.course c " +
                "JOIN cn.user u " + // 엔티티의 private StudentUser user; 필드와 매칭
                "WHERE c.courseId IN :courseIds")
        List<StudentCourseNoticeDTO> findNoticesByCourseIds(@Param("courseIds") List<Long> courseIds);
}