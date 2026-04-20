package com.samsamgyeesam.studyingvally.domain.user.dto;

// 학생, 강사 공통 정보 수정 DTO
public class UserInformationUpdateDTO {

    // 전화번호
    private String userPhoneNumber;

    // 이메일
    private String userEmail;

    // 비밀번호
    private String userPassword;

    // 닉네임
    private String userNickname;

    // 성별
    private String userGender;

    public UserInformationUpdateDTO() {
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}