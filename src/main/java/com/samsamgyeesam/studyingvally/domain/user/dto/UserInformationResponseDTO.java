package com.samsamgyeesam.studyingvally.domain.user.dto;

//학생 , 강사 공통 정보 조회 DTO
public class UserInformationResponseDTO {

    // 이름
    private String userName;

    // 전화번호
    private String userPhoneNumber;

    // 이메일
    private String userEmail;

    public UserInformationResponseDTO() {
    }

    public UserInformationResponseDTO(String userName, String userPhoneNumber, String userEmail) {
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
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
}