package com.samsamgyeesam.studyingvally.domain.notice.repository;

import com.samsamgyeesam.studyingvally.domain.notice.entity.TeacherNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherNoticeRepository extends JpaRepository<TeacherNotice, Long> {

    // 공지사항 전체 조회
    List<TeacherNotice> findAllByOrderByCreatedDateDesc();

}
