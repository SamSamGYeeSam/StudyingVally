package com.samsamgyeesam.studyingvally.domain.user.service;

import com.samsamgyeesam.studyingvally.domain.user.entity.UserAdmin;
import com.samsamgyeesam.studyingvally.domain.user.entity.UserUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Spring Security 세션에 저장될 인증 사용자 객체이다.
 *
 * 현재는 User와 Admin을 모두 처리할 수 있도록 확장한다.
 */

public class AuthUserDetails implements UserDetails {

    /**
     * 로그인 아이디
     */
    private final String loginId;

    /**
     * 비밀번호
     */
    private final String password;

    /**
     * 권한 문자열
     */
    private final String role;

    /**
     * 화면 표시용 이름
     */
    private final String displayName;

    private Long userNo;

    /**
     * 일반 사용자(User) 기반 생성자
     *
     * @param user 일반 사용자 엔티티
     */
    public AuthUserDetails(UserUser user) {
        this.loginId = user.getUserId();
        this.password = user.getUserPassword();
        this.role = "ROLE_" + user.getUserRole().name();
        this.displayName = user.getUserNickname();
        this.userNo = user.getUserNo();
    }

    /**
     * 관리자(Admin) 기반 생성자
     *
     * admin 테이블에는 role 컬럼이 없으므로
     * Security 내부에서는 ROLE_ADMIN으로 고정한다.
     *
     * @param admin 관리자 엔티티
     */
    public AuthUserDetails(UserAdmin admin) {
        this.loginId = admin.getAdminId();
        this.password = admin.getAdminPassword();
        this.role = "ROLE_ADMIN";
        this.displayName = "관리자";
    }

    /**
     * 화면에서 현재 로그인한 사용자명을 표시할 때 사용할 수 있다.
     *
     * @return 표시용 이름
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 현재 사용자의 권한 목록을 반환한다.
     *
     * @return 권한 컬렉션
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    /**
     * 비밀번호 반환
     *
     * @return 비밀번호
     */
    @Override
    public String getPassword() {
        return password;
    }

    public Long getUserNo() {
        return userNo;
    }

    /**
     * 로그인 아이디 반환
     *
     * @return 로그인 아이디
     */
    @Override
    public String getUsername() {
        return loginId;
    }

    /**
     * 계정 만료 여부
     *
     * 현재는 별도 관리하지 않으므로 true
     *
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠금 여부
     *
     * 현재는 별도 관리하지 않으므로 true
     *
     * @return true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 비밀번호 만료 여부
     *
     * 현재는 별도 관리하지 않으므로 true
     *
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정 활성 여부
     *
     * 현재는 별도 관리하지 않으므로 true
     *
     * @return true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}