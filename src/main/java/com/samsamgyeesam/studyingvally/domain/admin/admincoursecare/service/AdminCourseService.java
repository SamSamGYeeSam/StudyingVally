package com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.service;

import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.dto.AdminChapterResponseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.dto.AdminCourseDetailResponseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.dto.AdminManagedCourseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.entity.AdminChapter;
import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.entity.AdminCourse;
import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.repository.AdminChapterRepository;
import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.repository.AdminCourseRepository;
import com.samsamgyeesam.studyingvally.domain.admin.exception.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCourseService {

    private final AdminCourseRepository adminCourseRepository;
    private final AdminChapterRepository adminChapterRepository;

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public List<AdminManagedCourseDTO> findAllCourses() {
        return adminCourseRepository.findByCourseSendApproveTrueOrderByCourseIdDesc()
                .stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    public List<AdminManagedCourseDTO> findCoursesByStatus(String status) {
        return adminCourseRepository.findByCourseSendApproveTrueAndCourseStatusOrderByCourseIdDesc(status)
                .stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    public AdminCourseDetailResponseDTO findCourseDetail(Long courseId) {
        AdminCourse adminCourse = adminCourseRepository.findDetailByCourseId(courseId)
                .orElseThrow(() -> new AdminException("해당 강의가 존재하지 않습니다."));

        validateApprovedCourse(adminCourse);

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

    @Transactional
    public void openCourse(Long courseId) {
        AdminCourse adminCourse = adminCourseRepository.findDetailByCourseId(courseId)
                .orElseThrow(() -> new AdminException("해당 강의가 존재하지 않습니다."));

        validateApprovedCourse(adminCourse);

        adminCourse.changeCourseStatus("OPEN");
    }

    @Transactional
    public void closeCourse(Long courseId) {
        AdminCourse adminCourse = adminCourseRepository.findDetailByCourseId(courseId)
                .orElseThrow(() -> new AdminException("해당 강의가 존재하지 않습니다."));

        validateApprovedCourse(adminCourse);

        adminCourse.changeCourseStatus("CLOSED");
    }

    private void validateApprovedCourse(AdminCourse adminCourse) {
        if (!Boolean.TRUE.equals(adminCourse.getCourseSendApprove())) {
            throw new AdminException("승인 완료된 강의만 조회 및 상태 변경할 수 있습니다.");
        }
    }

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

    private AdminChapterResponseDTO toChapterDTO(AdminChapter adminChapter) {
        return new AdminChapterResponseDTO(
                adminChapter.getChapNo(),
                adminChapter.getChapTitle(),
                adminChapter.getChapDesc(),
                adminChapter.getChapUrl()
        );
    }

    private String convertCourseStatusToKorean(String courseStatus) {
        if ("OPEN".equalsIgnoreCase(courseStatus)) {
            return "활성화";
        }
        if ("CLOSED".equalsIgnoreCase(courseStatus)) {
            return "비활성화";
        }
        return courseStatus;
    }
}