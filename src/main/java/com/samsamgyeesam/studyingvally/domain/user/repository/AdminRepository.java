package com.samsamgyeesam.studyingvally.domain.user.repository;

import com.samsamgyeesam.studyingvally.domain.user.entity.UserAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * admin 테이블 조회를 담당하는 Repository이다.
 */
public interface AdminRepository extends JpaRepository<UserAdmin, Long> {

    /**
     * 관리자 로그인 아이디로 관리자를 조회한다.
     *
     * @param adminId 관리자 로그인 아이디
     * @return Admin Optional
     */
    Optional<UserAdmin> findByAdminId(String adminId);
}