package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* comment.
 * 관리자 신고 답변 등록/수정 요청 DTO
 *
 * 왜 필요한가:
 * - 신고 상세 페이지에서 전달된 신고 번호와 답변 내용을 받기 위해 사용한다.
 */
@Getter
@Setter
@NoArgsConstructor
public class AdminReportAnswerRequestDTO {

    /* comment.
     * 답변 대상 신고 번호
     */
    private Long reportNo;

    /* comment.
     * 처리 답변 내용
     */
    private String reportAnswer;
}