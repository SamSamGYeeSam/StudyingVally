package com.samsamgyeesam.studyingvally.domain.admin.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/* comment.
 * 관리자 신고함 목록 DTO
 *
 * 왜 필요한가:
 * - 신고함 목록 화면에 필요한 값만 전달하기 위해 사용한다.
 * - displayNo는 프론트에서 처리하므로 DTO에서 제외한다.
 */
@Getter
@AllArgsConstructor
public class AdminReportListDTO {

    /* comment.
     * 실제 신고 PK
     */
    private Long reportNo;

    /* comment.
     * 신고 제목
     */
    private String reportTitle;

    /* comment.
     * 사용자 이름
     */
    private String userName;

    /* comment.
     * 사용자 닉네임
     */
    private String userNickname;

    /* comment.
     * 화면 표시용 처리 상태
     */
    private String reportStatusLabel;

    /* comment.
     * 처리 일시
     */
    private LocalDateTime reportProcessedAt;
}