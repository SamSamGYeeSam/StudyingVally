package com.samsamgyeesam.studyingvally.domain.user.repository;
import com.samsamgyeesam.studyingvally.domain.user.entity.UserUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * user 테이블 조회를 담당하는 Repository이다.
 */
public interface UserRepository extends JpaRepository<UserUser, Long> {

    /*
     * 로그인 아이디로 사용자를 조회한다.
     */
    Optional<UserUser> findByUserId(String userId);
    /*
     * 이름과 전화번호로 사용자를 조회한다.
     * 아이디 찾기 기능에서 사용한다.
     */
    Optional<UserUser> findByUserNameAndUserPhoneNumber(String userName, String userPhoneNumber);

    // 아이디와 전화번호를 조회해 비밀번호를 찾는다.
    Optional<UserUser> findByUserIdAndUserPhoneNumber(String userId, String userPhoneNumber);

    /* 회원가입 시 아이디, 이메일, 전화번호, 닉네임 중복 검사 */
    boolean existsByUserId(String userId);
    boolean existsByUserEmail(String userEmail);
    boolean existsByUserPhoneNumber(String userPhoneNumber);
    boolean existsByUserNickname(String userNickname);

}