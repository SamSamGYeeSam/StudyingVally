package com.samsamgyeesam.studyingvally.domain.admin.service;

import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminUserDetailResponseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminUserListResponseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminCourse;
import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminEnrollment;
import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminReportCount;
import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminUser;
import com.samsamgyeesam.studyingvally.domain.admin.repository.AdminCourseRepository;
import com.samsamgyeesam.studyingvally.domain.admin.repository.AdminEnrollmentRepository;
import com.samsamgyeesam.studyingvally.domain.admin.repository.AdminQuestionTechRepository;
import com.samsamgyeesam.studyingvally.domain.admin.repository.AdminReportCountRepository;
import com.samsamgyeesam.studyingvally.domain.admin.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/* comment.
 * 관리자 사용자 관리 서비스 클래스
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final AdminCourseRepository adminCourseRepository;
    private final AdminEnrollmentRepository adminEnrollmentRepository;
    private final AdminQuestionTechRepository adminQuestionTechRepository;
    private final AdminReportCountRepository adminReportCountRepository;

    /* comment.
     * 전체 사용자 목록 조회 메서드
     */
    public List<AdminUserListResponseDTO> findAllUsers() {
        return adminUserRepository.findAllByOrderByUserNoDesc()
                .stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    /* comment.
     * 역할별 사용자 목록 조회 메서드
     */
    public List<AdminUserListResponseDTO> findUsersByRole(String role) {
        return adminUserRepository.findByUserRoleOrderByUserNoDesc(role)
                .stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    /* comment.
     * 사용자 상세 조회 메서드
     */
    public AdminUserDetailResponseDTO findUserDetail(Long userNo) {

        AdminUser adminUser = adminUserRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        List<String> courseTitleList = Collections.emptyList();
        String courseSectionTitle = "";

        if ("TEACHER".equalsIgnoreCase(adminUser.getUserRole())) {
            courseTitleList = adminCourseRepository.findByTeacher_UserNoOrderByCourseIdDesc(userNo)
                    .stream()
                    .map(AdminCourse::getCourseTitle)
                    .collect(Collectors.toList());

            courseSectionTitle = "개설한 강의";
        } else if ("STUDENT".equalsIgnoreCase(adminUser.getUserRole())) {
            List<Long> courseIds = adminEnrollmentRepository.findByUserNoOrderByEnrollmentNoDesc(userNo)
                    .stream()
                    .map(AdminEnrollment::getCourseId)
                    .collect(Collectors.toList());

            if (!courseIds.isEmpty()) {
                courseTitleList = adminCourseRepository.findByCourseIdInOrderByCourseIdDesc(courseIds)
                        .stream()
                        .map(AdminCourse::getCourseTitle)
                        .collect(Collectors.toList());
            }

            courseSectionTitle = "신청한 강의";
        }

        long inquiryCount = adminQuestionTechRepository.countByUserNo(userNo);

        int reportCount = adminReportCountRepository.findByUserNo(userNo)
                .map(AdminReportCount::getReportCount)
                .orElse(0);

        return new AdminUserDetailResponseDTO(
                adminUser.getUserNo(),
                adminUser.getUserName(),
                adminUser.getUserNickname(),
                adminUser.getUserPhoneNumber(),
                adminUser.getUserRole(),
                adminUser.getUserStatus(),
                convertRoleToKorean(adminUser.getUserRole()),
                convertStatusToKorean(adminUser.getUserStatus()),
                courseSectionTitle,
                courseTitleList,
                inquiryCount,
                reportCount
        );
    }

    /* comment.
     * 사용자 활성화 메서드
     */
    @Transactional
    public void enableUser(Long userNo) {
        AdminUser adminUser = adminUserRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        adminUser.changeUserStatus("ACTIVE");
    }

    /* comment.
     * 사용자 비활성화 메서드
     */
    @Transactional
    public void disableUser(Long userNo) {
        AdminUser adminUser = adminUserRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        adminUser.changeUserStatus("INACTIVE");
    }

    /* comment.
     * 엔티티를 목록 DTO로 변환하는 메서드
     */
    private AdminUserListResponseDTO toListDTO(AdminUser adminUser) {
        return new AdminUserListResponseDTO(
                adminUser.getUserNo(),
                adminUser.getUserName(),
                adminUser.getUserNickname(),
                adminUser.getUserPhoneNumber(),
                adminUser.getUserRole(),
                adminUser.getUserStatus(),
                convertRoleToKorean(adminUser.getUserRole()),
                convertStatusToKorean(adminUser.getUserStatus())
        );
    }

    /* comment.
     * 역할 값을 한글로 변환하는 메서드
     */
    private String convertRoleToKorean(String userRole) {
        if ("TEACHER".equalsIgnoreCase(userRole)) {
            return "선생님";
        }
        if ("STUDENT".equalsIgnoreCase(userRole)) {
            return "학생";
        }
        return userRole;
    }

    /* comment.
     * 상태 값을 한글로 변환하는 메서드
     */
    private String convertStatusToKorean(String userStatus) {
        if ("ACTIVE".equalsIgnoreCase(userStatus)) {
            return "활성화";
        }
        if ("INACTIVE".equalsIgnoreCase(userStatus)) {
            return "비활성화";
        }
        return userStatus;
    }
}