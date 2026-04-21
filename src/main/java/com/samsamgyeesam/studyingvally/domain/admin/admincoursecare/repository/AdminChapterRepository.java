package com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.repository;

import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.entity.AdminChapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface AdminChapterRepository extends JpaRepository<AdminChapter, Long> {

    List<AdminChapter> findByCourseIdOrderByChapNoAsc(Long courseId);
}