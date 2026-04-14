package com.samsamgyeesam.studyingvally.domain.user.repository;
import com.samsamgyeesam.studyingvally.domain.user.entity.UserUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * user 테이블 조회를 담당하는 Repository이다.
 */
public interface UserRepository extends JpaRepository<UserUser, Long> {

    /**
     * 로그인 아이디로 사용자를 조회한다.
     *
     * @param userId 로그인 아이디
     * @return User Optional
     */
    Optional<UserUser> findByUserId(String userId);
}