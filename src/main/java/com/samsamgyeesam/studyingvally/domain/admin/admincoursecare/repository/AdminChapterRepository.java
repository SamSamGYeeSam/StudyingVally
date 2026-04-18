package com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.repository;

import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.entity.AdminChapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* comment.
 * 관리자 강의 상세 화면에서 챕터 정보를 조회하기 위한 Repository 인터페이스
 */

public interface AdminChapterRepository extends JpaRepository<AdminChapter, Long> {

    List<AdminChapter> findByCourseIdOrderByChapNoAsc(Long courseId);
}