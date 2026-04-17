package com.samsamgyeesam.studyingvally.domain.admin.repository;

import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/* comment.
 * 관리자 신고함 Repository
 *
 * 왜 필요한가:
 * - 신고 목록 조회
 * - 신고 상세 조회
 * - 답변 처리 대상 조회
 *
 * 주의할 점:
 * - 목록/상세에서 사용자 이름과 닉네임이 필요하므로 user를 함께 조회한다.
 */
public interface AdminReportRepository extends JpaRepository<AdminReport, Long> {

    /* comment.
     * 신고 목록 조회
     *
     * 왜 join fetch를 사용하는가:
     * - 신고 목록에서 user 이름/닉네임을 함께 보여줘야 하므로
     *   user를 같이 조회하여 N+1 문제를 줄인다.
     */
    @Query("""
            select r
            from AdminReport r
            join fetch r.user u
            order by r.reportNo desc
            """)
    List<AdminReport> findAllWithUserOrderByReportNoDesc();

    /* comment.
     * 신고 상세 조회
     *
     * 왜 join fetch를 사용하는가:
     * - 상세 화면에서도 user 이름/닉네임이 필요하기 때문이다.
     */
    @Query("""
            select r
            from AdminReport r
            join fetch r.user u
            where r.reportNo = :reportNo
            """)
    Optional<AdminReport> findDetailByReportNo(Long reportNo);
}