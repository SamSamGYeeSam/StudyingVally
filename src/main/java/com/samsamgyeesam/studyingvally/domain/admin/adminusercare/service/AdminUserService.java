package com.samsamgyeesam.studyingvally.domain.admin.adminusercare.service;

import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.repository.AdminQuestionTechRepository;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.repository.AdminReportCountRepository;
import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.entity.AdminCourse;
import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.entity.AdminEnrollment;
import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.repository.AdminCourseRepository;
import com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.repository.AdminEnrollmentRepository;
import com.samsamgyeesam.studyingvally.domain.admin.adminusercare.dto.AdminUserDetailResponseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.adminusercare.dto.AdminUserListResponseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.adminusercare.entity.AdminReportCount;
import com.samsamgyeesam.studyingvally.domain.admin.adminusercare.entity.AdminUser;
import com.samsamgyeesam.studyingvally.domain.admin.adminusercare.repository.AdminUserRepository;
import com.samsamgyeesam.studyingvally.domain.admin.exception.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final AdminCourseRepository adminCourseRepository;
    private final AdminEnrollmentRepository adminEnrollmentRepository;
    private final AdminQuestionTechRepository adminQuestionTechRepository;
    private final AdminReportCountRepository adminReportCountRepository;

    public List<AdminUserListResponseDTO> findAllUsers() {
        return adminUserRepository.findAllByOrderByUserNoDesc()
                .stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    public List<AdminUserListResponseDTO> findUsersByRole(String role) {
        return adminUserRepository.findByUserRoleOrderByUserNoDesc(role)
                .stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    public AdminUserDetailResponseDTO findUserDetail(Long userNo) {
        AdminUser adminUser = adminUserRepository.findById(userNo)
                .orElseThrow(() -> new AdminException("해당 사용자가 존재하지 않습니다."));

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

        long inquiryCount = adminQuestionTechRepository.countByUser_UserNo(userNo);

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

    @Transactional
    public void enableUser(Long userNo) {
        AdminUser adminUser = adminUserRepository.findById(userNo)
                .orElseThrow(() -> new AdminException("해당 사용자가 존재하지 않습니다."));

        adminUser.changeUserStatus("ACTIVE");
    }

    @Transactional
    public void disableUser(Long userNo) {
        AdminUser adminUser = adminUserRepository.findById(userNo)
                .orElseThrow(() -> new AdminException("해당 사용자가 존재하지 않습니다."));

        adminUser.changeUserStatus("INACTIVE");
    }

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

    private String convertRoleToKorean(String userRole) {
        if ("TEACHER".equalsIgnoreCase(userRole)) {
            return "선생님";
        }
        if ("STUDENT".equalsIgnoreCase(userRole)) {
            return "학생";
        }
        return userRole;
    }

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