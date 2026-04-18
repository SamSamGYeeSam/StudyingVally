package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.repository;

import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.entity.AdminQuestionTech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * 관리자 문의함 Repository
 *
 * 왜 필요한가:
 * - question_tech 테이블의 목록 조회, 상세 조회, 수정 대상 조회를 담당한다.
 */
public interface AdminQuestionTechRepository extends JpaRepository<AdminQuestionTech, Long> {

    /**
     * 사용자별 문의 수 조회
     *
     * 기존 사용자 상세 화면 등에서 사용할 수 있도록 유지한다.
     */
    long countByUser_UserNo(Long userNo);

    /**
     * 문의 목록 조회
     *
     * 왜 join fetch를 사용하는가:
     * - 목록에서 user 이름/닉네임을 함께 보여줘야 하므로
     *   question_tech와 user를 같이 조회하여 N+1 문제를 줄인다.
     */
    @Query("""
            select qt
            from AdminQuestionTech qt
            join fetch qt.user u
            order by qt.questionTechNo desc
            """)
    List<AdminQuestionTech> findAllWithUserOrderByQuestionTechNoDesc();

    /**
     * 문의 상세 조회
     *
     * 왜 join fetch를 사용하는가:
     * - 상세 화면에서도 user 이름/닉네임이 필요하므로 함께 조회한다.
     */
    @Query("""
            select qt
            from AdminQuestionTech qt
            join fetch qt.user u
            where qt.questionTechNo = :questionTechNo
            """)
    Optional<AdminQuestionTech> findDetailByQuestionTechNo(Long questionTechNo);
}