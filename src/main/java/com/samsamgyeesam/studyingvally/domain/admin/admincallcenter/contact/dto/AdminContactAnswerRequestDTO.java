package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 상세 페이지에서 전달된 문의 번호와 답변 내용을 받기 위해 사용
 */
@Getter
@Setter
@NoArgsConstructor
public class AdminContactAnswerRequestDTO {


    private Long questionTechNo;
    private String questionAnswer;
}