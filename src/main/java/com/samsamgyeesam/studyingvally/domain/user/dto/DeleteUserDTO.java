package com.samsamgyeesam.studyingvally.domain.user.dto;

public class DeleteUserDTO {
    private String userPassword;

    public DeleteUserDTO() {}

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
