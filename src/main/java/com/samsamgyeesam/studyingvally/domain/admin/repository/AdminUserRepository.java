package com.samsamgyeesam.studyingvally.domain.admin.repository;

import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* comment.
 *  관리자 사용자 관리 전용 Repository 인터페이스
 */

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    /* comment.
     *  역할별 사용자 목록 조회 메서드
     */

    List<AdminUser> findByUserRole(String userRole);
}