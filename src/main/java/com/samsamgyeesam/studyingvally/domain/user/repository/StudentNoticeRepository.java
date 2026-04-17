package com.samsamgyeesam.studyingvally.domain.user.repository;

import com.samsamgyeesam.studyingvally.domain.admin.dto.StudentAdminNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.admin.entity.StudentAdminNotice;
import com.samsamgyeesam.studyingvally.domain.course.dto.StudentCourseNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.StudentCourseNotice;
import com.samsamgyeesam.studyingvally.domain.user.entity.StudentNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentNoticeRepository extends JpaRepository<StudentAdminNotice, Long> {

    // 1. 관리자 공지 조회 (CAST 대신 str() 사용 혹은 직접 참조)
    @Query("SELECT new com.samsamgyeesam.studyingvally.domain.admin.dto.StudentAdminNoticeDTO(" +
            "an.noticeNo, str(an.createdAt), an.noticeTitle, an.noticeDesc) " +
            "FROM StudentAdminNotice an ORDER BY an.createdAt DESC")
    List<StudentAdminNoticeDTO> findAllAdminNotices();

    // 2. 강의 공지 조회
    @Query("SELECT new com.samsamgyeesam.studyingvally.domain.course.dto.StudentCourseNoticeDTO(" +
            "cn.courseNoticeNo, str(cn.createdAt), c.courseTitle, u.userName, cn.courseNoticeTitle, cn.courseNoticeDesc) " +
            "FROM StudentCourseNotice cn " +
            "JOIN cn.course c " +
            "JOIN cn.user u " +
            "WHERE c.courseId IN :courseIds")
    List<StudentCourseNoticeDTO> findMyCourseNotices(@Param("courseIds") List<Long> courseIds);
}