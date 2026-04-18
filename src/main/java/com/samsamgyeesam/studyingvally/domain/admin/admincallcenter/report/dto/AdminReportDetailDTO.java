package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/* comment.
 * 관리자 신고함 상세 DTO
 *
 * 왜 필요한가:
 * - 신고 상세 화면에서 신고 내용과 답변 작성 영역에 필요한 값을 전달하기 위해 사용한다.
 */
@Getter
@AllArgsConstructor
public class AdminReportDetailDTO {

    private Long reportNo;
    private String reportTitle;
    private String reportDesc;
    private String userName;
    private String userNickname;
    private String reportStatusLabel;
    private String reportAnswer;
    private LocalDateTime reportProcessedAt;
    private LocalDateTime reportAnswerUpdatedAt;
}