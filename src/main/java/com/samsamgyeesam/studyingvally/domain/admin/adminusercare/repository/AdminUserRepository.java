package com.samsamgyeesam.studyingvally.domain.admin.adminusercare.repository;

import com.samsamgyeesam.studyingvally.domain.admin.adminusercare.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* comment.
 * 관리자 사용자 관리 기능에서 사용자 데이터를 조회하기 위한 Repository 인터페이스
 */

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    List<AdminUser> findAllByOrderByUserNoDesc();

    List<AdminUser> findByUserRoleOrderByUserNoDesc(String userRole);
}