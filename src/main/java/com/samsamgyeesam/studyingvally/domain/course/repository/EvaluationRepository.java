package com.samsamgyeesam.studyingvally.domain.course.repository;

import com.samsamgyeesam.studyingvally.domain.course.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    // 강의 삭제 시 그에 딸린 강의평도 함께 삭제
    void deleteByCourseId(Long courseId);

//    // 강의평 조회 - 강의ID로
//    List<Evaluation> findByCourseIdOrderByEvaluationNoDesc(Long courseId);

    // 강의평을 쓴 학생의 이름, 닉네임 가져오기
    @Query("SELECT e FROM Evaluation e JOIN FETCH e.user WHERE e.courseId = :courseId")
    List<Evaluation> findByCourseIdWithUser(@Param("courseId") Long courseId);


}
