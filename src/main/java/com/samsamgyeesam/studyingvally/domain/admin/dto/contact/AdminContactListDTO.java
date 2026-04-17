package com.samsamgyeesam.studyingvally.domain.admin.dto;

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

    /**
     * 실제 문의 PK
     */
    private Long questionTechNo;

    /**
     * 문의 제목
     */
    private String questionTitle;

    /**
     * 사용자 이름
     */
    private String userName;

    /**
     * 사용자 닉네임
     */
    private String userNickname;

    /**
     * 화면 표시용 상태명
     *
     * 예:
     * - 대기
     * - 완료
     */
    private String questionStatusLabel;

    /**
     * 답변 일시
     */
    private LocalDateTime questionAnsweredAt;
}