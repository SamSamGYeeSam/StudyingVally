package com.samsamgyeesam.studyingvally.domain.user.service;

import com.samsamgyeesam.studyingvally.domain.user.entity.UserAdmin;
import com.samsamgyeesam.studyingvally.domain.user.repository.AdminRepository;
import com.samsamgyeesam.studyingvally.domain.user.entity.UserUser;
import com.samsamgyeesam.studyingvally.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security가 로그인 시 호출하는 사용자 조회 서비스이다.
 *
 * 현재는
 * 1. admin 테이블 먼저 조회
 * 2. 없으면 user 테이블 조회
 *
 * 순서로 동작한다.
 */
@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    /**
     * 일반 사용자 조회용 Repository
     */
    private final UserRepository userRepository;

    /**
     * 관리자 조회용 Repository
     */
    private final AdminRepository adminRepository;

    /**
     * 로그인 아이디로 사용자를 조회한다.
     *
     * @param loginId 로그인 아이디
     * @return UserDetails 구현체
     * @throws UsernameNotFoundException user/admin 모두 없을 때 예외
     */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        /**
         * 1. 관리자 계정부터 먼저 조회한다.
         *
         * 이유:
         * - admin 테이블이 별도로 존재한다.
         * - 관리자 계정은 별도 권한으로 처리해야 한다.
         */
        UserAdmin admin = adminRepository.findByAdminId(loginId).orElse(null);

        if (admin != null) {
            return new AuthUserDetails(admin);
        }

        /**
         * 2. 관리자가 아니면 일반 사용자(user) 테이블에서 조회한다.
         */
        UserUser user = userRepository.findByUserId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("일치하는 계정을 찾을 수 없습니다."));

        return new AuthUserDetails(user);
    }
}