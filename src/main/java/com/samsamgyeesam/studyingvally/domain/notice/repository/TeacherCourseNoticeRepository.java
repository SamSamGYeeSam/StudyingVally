package com.samsamgyeesam.studyingvally.domain.notice.repository;

import com.samsamgyeesam.studyingvally.domain.notice.entity.TeacherCourseNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherCourseNoticeRepository extends JpaRepository<TeacherCourseNotice,Long> {

//    // 전체 강의 소식 조회
//    List<TeacherCourseNotice> findByUserNoOrderByCourseNoticeNoDesc(Long userNo);

    // 강의 삭제 시 강의소식도 삭제
    void deleteByCourseId(Long courseId);

    // 강사번호(사용자번호)로 강의소식 조회
    @Query("SELECT n FROM TeacherCourseNotice n JOIN FETCH n.course WHERE n.course.userNo = :userNo ORDER BY n.courseNoticeNo DESC")
    List<TeacherCourseNotice> findByUserNoWithCourse(@Param("userNo") Long userNo);

    // 강의번호로 강의소식 찾기
    @Query("SELECT n FROM TeacherCourseNotice n JOIN FETCH n.course WHERE n.courseId = :courseId")
    List<TeacherCourseNotice> findByCourseIdWithCourse(@Param("courseId") Long courseId);

}
