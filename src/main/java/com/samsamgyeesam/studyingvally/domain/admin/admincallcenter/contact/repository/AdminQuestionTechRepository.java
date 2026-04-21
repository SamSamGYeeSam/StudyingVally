package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.repository;

import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.entity.AdminQuestionTech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdminQuestionTechRepository extends JpaRepository<AdminQuestionTech, Long> {


    long countByUser_UserNo(Long userNo);

    /*
     * 문의 목록 조회 */

    @Query("""
            select qt
            from AdminQuestionTech qt
            join fetch qt.user u
            order by qt.questionTechNo desc
            """)
    List<AdminQuestionTech> findAllWithUserOrderByQuestionTechNoDesc();

    /*
     * 문의 상세 조회 */

    @Query("""
            select qt
            from AdminQuestionTech qt
            join fetch qt.user u
            where qt.questionTechNo = :questionTechNo
            """)
    Optional<AdminQuestionTech> findDetailByQuestionTechNo(Long questionTechNo);
}