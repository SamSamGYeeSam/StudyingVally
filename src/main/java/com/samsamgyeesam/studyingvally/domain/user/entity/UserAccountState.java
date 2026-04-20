package com.samsamgyeesam.studyingvally.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user_account_state")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccountState {

    @Id
    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "login_fail_count", nullable = false)
    private int loginFailCount;

    @Column(name = "is_account_locked", nullable = false)
    private boolean accountLocked;

    public static UserAccountState create(Long userNo) {
        UserAccountState state = new UserAccountState();
        state.userNo = userNo;
        state.loginFailCount = 0;
        state.accountLocked = false;
        return state;
    }

    public void increaseLoginFailCount() {
        this.loginFailCount++;
    }

    public void resetLoginFailCount() {
        this.loginFailCount = 0;
    }

    public void lockAccount() {
        this.accountLocked = true;
    }

    public void unlockAccount() {
        this.accountLocked = false;
    }
}