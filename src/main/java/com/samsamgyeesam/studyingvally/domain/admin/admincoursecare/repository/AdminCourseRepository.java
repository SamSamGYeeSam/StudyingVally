package com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.repository;

import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.entity.AdminCourse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/* comment.
 *  관리자 사용자 상세 화면 및 강의 관리 화면에서 강의 정보를 조회하기 위한 Repository 인터페이스
 *  공부하기!!
 */

public interface AdminCourseRepository extends JpaRepository<AdminCourse, Long> {

    /**
     * 특정 강사가 개설한 강의 목록 조회
     *
     * @param userNo 강사 사용자 번호
     * @return 강의 엔티티 리스트
     */
    @EntityGraph(attributePaths = "teacher")
    List<AdminCourse> findByTeacher_UserNoOrderByCourseIdDesc(Long userNo);

    /**
     * 강의 번호 목록으로 강의 목록 조회
     *
     * @param courseIds 강의 번호 리스트
     * @return 강의 엔티티 리스트
     */
    @EntityGraph(attributePaths = "teacher")
    List<AdminCourse> findByCourseIdInOrderByCourseIdDesc(List<Long> courseIds);

    /**
     * 승인 요청 완료된 전체 강의 목록 조회
     *
     * @return 강의 엔티티 리스트
     */
    @EntityGraph(attributePaths = "teacher")
    List<AdminCourse> findByCourseSendApproveTrueOrderByCourseIdDesc();

    /**
     * 승인 요청 완료된 강의 중 상태별 목록 조회
     *
     * @param courseStatus 강의 상태
     * @return 강의 엔티티 리스트
     */
    @EntityGraph(attributePaths = "teacher")
    List<AdminCourse> findByCourseSendApproveTrueAndCourseStatusOrderByCourseIdDesc(String courseStatus);

    /**
     * 강의 상세 조회
     *
     * 왜 필요한가:
     * - 상세 페이지에서 teacher 정보까지 함께 안전하게 조회하기 위함이다.
     * - LAZY 로딩으로 인한 예기치 않은 문제를 방지한다.
     */
    @EntityGraph(attributePaths = "teacher")
    Optional<AdminCourse> findDetailByCourseId(Long courseId);
}