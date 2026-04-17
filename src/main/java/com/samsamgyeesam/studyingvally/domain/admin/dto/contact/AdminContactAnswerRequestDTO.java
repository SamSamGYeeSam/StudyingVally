package com.samsamgyeesam.studyingvally.domain.admin.dto.contact;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 관리자 문의 답변 등록/수정 요청 DTO
 *
 * 왜 필요한가:
 * - 상세 페이지에서 전달된 문의 번호와 답변 내용을 받기 위해 사용한다.
 */
@Getter
@Setter
@NoArgsConstructor
public class AdminContactAnswerRequestDTO {

    /**
     * 답변 대상 문의 번호
     */
    private Long questionTechNo;

    /**
     * 관리자 답변 내용
     */
    private String questionAnswer;
}