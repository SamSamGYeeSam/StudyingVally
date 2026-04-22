package com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.repository;

import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.entity.AdminEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface AdminEnrollmentRepository extends JpaRepository<AdminEnrollment, Long> {

    List<AdminEnrollment> findByUserNoOrderByEnrollmentNoDesc(Long userNo);
}