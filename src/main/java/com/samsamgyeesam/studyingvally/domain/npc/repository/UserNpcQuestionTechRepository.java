package com.samsamgyeesam.studyingvally.domain.npc.repository;

import com.samsamgyeesam.studyingvally.domain.npc.entity.UserNpcQuestionTech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNpcQuestionTechRepository extends JpaRepository<UserNpcQuestionTech, Long> {

    // 유저 번호로 조회하되, 최신순(내림차순)으로 정렬하여 가져옵니다.
    List<UserNpcQuestionTech> findByUserNoOrderByQuestionTechNoDesc(Long userNo);
}