package com.samsamgyeesam.studyingvally.domain.user.entity;

import com.samsamgyeesam.studyingvally.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

/**
 * user 테이블과 매핑되는 엔티티이다.
 *
 * 현재 로그인에서 핵심적으로 사용하는 컬럼:
 * - user_id
 * - user_password
 * - user_role
 *
 * 그 외 컬럼도 이후 마이페이지, 회원정보 조회 등에 사용할 수 있으므로 함께 매핑한다.
 */
@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUser extends BaseTimeEntity {

    /**
     * 사용자 PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;

    /**
     * 로그인 아이디
     */
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    /**
     * 비밀번호
     *
     * 현재는 평문 상태를 그대로 사용한다.
     */
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    /**
     * 전화번호
     */
    @Column(name = "user_phone_number", unique = true)
    private String userPhoneNumber;

    /**
     * 이메일
     */
    @Column(name = "user_email", unique = true)
    private String userEmail;

    /**
     * 사용자 권한
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    /**
     * 닉네임
     */
    @Column(name = "user_nickname", unique = true)
    private String userNickname;

    /**
     * 사용자 이름
     */
    @Column(name = "user_name", nullable = false)
    private String userName;

    /* 상태 */
    @Column(name = "user_status", nullable = false)
    private String userStatus;

    /* 성별 */
    @Column(name = "user_gender", nullable = false)
    private String userGender;

    public static UserUser builder() {
        return new UserUser();
    }

    public UserUser userId(String userId) {
        this.userId = userId;
        return this;
    }

    public UserUser userPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }

    public UserUser userPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
        return this;
    }

    public UserUser userEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public UserUser userRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public UserUser userNickname(String userNickname) {
        this.userNickname = userNickname;
        return this;
    }

    public UserUser userName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserUser userStatus(String userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public UserUser userGender(String userGender) {
        this.userGender = userGender;
        return this;
    }

    /*
     * 학생 / 강사 공통 정보 수정 메서드
     */
    public void updateInformation(String userPhoneNumber, String userEmail, String userPassword) {
        this.userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

}