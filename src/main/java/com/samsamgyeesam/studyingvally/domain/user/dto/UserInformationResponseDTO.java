package com.samsamgyeesam.studyingvally.domain.user.dto;

//학생 , 강사 공통 정보 조회 DTO
public class UserInformationResponseDTO {

    // 이름
    private String userName;

    // 전화번호
    private String userPhoneNumber;

    // 이메일
    private String userEmail;

    // 닉네임
    private String userNickname;

    //성별
    private String userGender;

    public UserInformationResponseDTO() {
    }

    public UserInformationResponseDTO(String userName, String userPhoneNumber, String userEmail, String userNickname, String userGender) {
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userGender = userGender;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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