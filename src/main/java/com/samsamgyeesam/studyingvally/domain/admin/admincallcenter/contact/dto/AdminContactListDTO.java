package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 관리자 문의함 목록 DTO
 *
 * 왜 필요한가:
 * - 목록 화면에 필요한 값만 전달하기 위해 사용한다.
 * - displayNo는 프론트에서 처리하므로 DTO에서 제외한다.
 */
@Getter
@AllArgsConstructor
public class AdminContactListDTO {

    private Long questionTechNo;
    private String questionTitle;
    private String userName;
    private String userNickname;
    private String questionStatusLabel;
    private LocalDateTime questionAnsweredAt;
}