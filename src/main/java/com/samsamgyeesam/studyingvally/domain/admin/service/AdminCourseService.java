package com.samsamgyeesam.studyingvally.domain.admin.service;

import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminChapterResponseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminCourseDetailResponseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminManagedCourseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminChapter;
import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminCourse;
import com.samsamgyeesam.studyingvally.domain.admin.repository.AdminChapterRepository;
import com.samsamgyeesam.studyingvally.domain.admin.repository.AdminCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/* comment.
 * 관리자 강의 관리 서비스 클래스
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCourseService {

    private final AdminCourseRepository adminCourseRepository;
    private final AdminChapterRepository adminChapterRepository;

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /* comment.
     * 개설 요청된 전체 강의 목록 조회 메서드
     */
    public List<AdminManagedCourseDTO> findAllCourses() {
        return adminCourseRepository.findByCourseSendApproveTrueOrderByCourseIdDesc()
                .stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    /* comment.
     * 개설 요청된 강의 중 상태별 목록 조회 메서드
     */
    public List<AdminManagedCourseDTO> findCoursesByStatus(String status) {
        return adminCourseRepository.findByCourseSendApproveTrueAndCourseStatusOrderByCourseIdDesc(status)
                .stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    /* comment.
     * 강의 상세 조회 메서드
     */
    public AdminCourseDetailResponseDTO findCourseDetail(Long courseId) {

        AdminCourse adminCourse = adminCourseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다."));

        List<AdminChapterResponseDTO> chapterList = adminChapterRepository.findByCourseIdOrderByChapNoAsc(courseId)
                .stream()
                .map(this::toChapterDTO)
                .collect(Collectors.toList());

        return new AdminCourseDetailResponseDTO(
                adminCourse.getCourseId(),
                adminCourse.getCourseTitle(),
                adminCourse.getCourseDescription(),
                adminCourse.getCourseCreatedAt().format(DATE_TIME_FORMATTER),
                adminCourse.getTeacher() != null ? adminCourse.getTeacher().getUserName() : "미지정",
                adminCourse.getCourseStatus(),
                convertCourseStatusToKorean(adminCourse.getCourseStatus()),
                chapterList
        );
    }

    /* comment.
     * 강의 활성화 메서드
     */
    @Transactional
    public void openCourse(Long courseId) {
        AdminCourse adminCourse = adminCourseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다."));

        changeCourseStatus(adminCourse, "OPEN");
    }

    /* comment.
     * 강의 비활성화 메서드
     */
    @Transactional
    public void closeCourse(Long courseId) {
        AdminCourse adminCourse = adminCourseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다."));

        changeCourseStatus(adminCourse, "CLOSED");
    }

    /* comment.
     * 목록 화면 DTO 변환 메서드
     */
    private AdminManagedCourseDTO toListDTO(AdminCourse adminCourse) {
        return new AdminManagedCourseDTO(
                adminCourse.getCourseId(),
                adminCourse.getCourseTitle(),
                adminCourse.getCourseDescription(),
                adminCourse.getCourseCreatedAt().format(DATE_TIME_FORMATTER),
                adminCourse.getTeacher() != null ? adminCourse.getTeacher().getUserName() : "미지정",
                adminCourse.getCourseStatus(),
                convertCourseStatusToKorean(adminCourse.getCourseStatus())
        );
    }

    /* comment.
     * 챕터 DTO 변환 메서드
     */
    private AdminChapterResponseDTO toChapterDTO(AdminChapter adminChapter) {
        return new AdminChapterResponseDTO(
                adminChapter.getChapNo(),
                adminChapter.getChapTitle(),
                adminChapter.getChapDesc(),
                adminChapter.getChapUrl()
        );
    }

    /* comment.
     * 강의 상태 값을 한글로 변환하는 메서드
     */
    private String convertCourseStatusToKorean(String courseStatus) {
        if ("OPEN".equalsIgnoreCase(courseStatus)) {
            return "활성화";
        }
        if ("CLOSED".equalsIgnoreCase(courseStatus)) {
            return "비활성화";
        }
        return courseStatus;
    }

    /* comment.
     * 강의 상태 변경 메서드
     */
    private void changeCourseStatus(AdminCourse adminCourse, String courseStatus) {
        try {
            java.lang.reflect.Field statusField = AdminCourse.class.getDeclaredField("courseStatus");
            statusField.setAccessible(true);
            statusField.set(adminCourse, courseStatus);
        } catch (Exception e) {
            throw new IllegalStateException("강의 상태 변경에 실패했습니다.");
        }
    }
}