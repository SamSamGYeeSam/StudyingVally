package com.samsamgyeesam.studyingvally.domain.admin.repository;

import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminQuestionTech;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* comment.
 * 관리자 사용자 상세 화면에서 문의 수를 조회하기 위한 Repository 인터페이스
 */

public interface AdminQuestionTechRepository extends JpaRepository<AdminQuestionTech, Long> {

    long countByUserNo(Long userNo);

    List<AdminQuestionTech> findAllByOrderByQuestionTechNoDesc();


}