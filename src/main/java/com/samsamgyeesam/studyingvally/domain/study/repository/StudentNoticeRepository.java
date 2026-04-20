package com.samsamgyeesam.studyingvally.domain.study.repository;

import com.samsamgyeesam.studyingvally.domain.study.dto.StudentAdminNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentAdminNotice;
import com.samsamgyeesam.studyingvally.domain.study.dto.StudentCourseNoticeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentNoticeRepository extends JpaRepository<StudentAdminNotice, Long> {

    @Query("SELECT new com.samsamgyeesam.studyingvally.domain.study.dto.StudentAdminNoticeDTO(" +
            "an.noticeNo, str(an.createdAt), an.noticeTitle, an.noticeDesc) " +
            "FROM StudentAdminNotice an ORDER BY an.createdAt DESC")
    List<StudentAdminNoticeDTO> findAllAdminNotices();

    @Query("SELECT new com.samsamgyeesam.studyingvally.domain.study.dto.StudentCourseNoticeDTO(" +
            "cn.courseNoticeNo, str(cn.createdAt), c.courseTitle, cn.userName, cn.courseNoticeTitle, cn.courseNoticeDesc) " +
            "FROM StudentCourseNotice cn " +
            "JOIN cn.course c " +
            "WHERE c.courseId IN :courseIds")
    List<StudentCourseNoticeDTO> findMyCourseNotices(@Param("courseIds") List<Long> courseIds);
}