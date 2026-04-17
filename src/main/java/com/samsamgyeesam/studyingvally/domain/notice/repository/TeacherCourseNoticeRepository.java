package com.samsamgyeesam.studyingvally.domain.notice.repository;

import com.samsamgyeesam.studyingvally.domain.notice.entity.TeacherCourseNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherCourseNoticeRepository extends JpaRepository<TeacherCourseNotice,Long> {

    // 전체 강의 소식 조회
    List<TeacherCourseNotice> findByUserNoOrderByCourseNoticeNoDesc(Long userNo);

}
