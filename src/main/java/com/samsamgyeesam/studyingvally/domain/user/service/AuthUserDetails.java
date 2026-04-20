package com.samsamgyeesam.studyingvally.domain.user.service;

import com.samsamgyeesam.studyingvally.domain.user.entity.UserAccountState;
import com.samsamgyeesam.studyingvally.domain.user.entity.UserAdmin;
import com.samsamgyeesam.studyingvally.domain.user.entity.UserUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Spring Security 세션에 저장될 사용자 정보 객체
@Getter
public class AuthUserDetails implements UserDetails {

    // 로그인 아이디
    private final String loginId;

    // 비밀번호
    private final String password;

    /*
     * 권한 문자열
     * 예: ROLE_STUDENT, ROLE_TEACHER, ROLE_ADMIN
     */
    private final String role;

    // 화면에 표시할 이름
    private final String displayName;

    // 일반 사용자 PK
    private final Long userNo;

    // 계정 잠금 여부
    private final boolean accountLocked;

    // 계정 상태
    private final boolean enabled;

    // 일반 사용자 생성자
    public AuthUserDetails(UserUser user, UserAccountState state) {
        this.loginId = user.getUserId();
        this.password = user.getUserPassword();
        this.role = "ROLE_" + user.getUserRole().name();
        this.displayName = user.getUserNickname();
        this.userNo = user.getUserNo();
        this.accountLocked = state != null && state.isAccountLocked();
        this.enabled = user.isActive();
    }

    // 관리자 생성자
    public AuthUserDetails(UserAdmin admin) {
        this.loginId = admin.getAdminId();
        this.password = admin.getAdminPassword();
        this.role = "ROLE_ADMIN";
        this.displayName = "관리자";
        this.userNo = null;
        this.accountLocked = false;
        this.enabled = true;
    }

    // 현재 사용자의 권한 목록 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    // Security가 사용할 비밀번호 반환
    @Override
    public String getPassword() {
        return password;
    }

    //  Security가 사용할 username 반환
    @Override
    public String getUsername() {
        return loginId;
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
     * 계정 잠금 여부
     * false면 Spring Security가 로그인 차단
     */
    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    // 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성 여부
    @Override
    public boolean isEnabled() {
        return enabled;
    }


}