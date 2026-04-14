package com.samsamgyeesam.studyingvally.domain.admin.service;

import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminUserListResponseDTO;
import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminUser;
import com.samsamgyeesam.studyingvally.domain.admin.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/* comment.
 *  관리자 사용자 관리 서비스 클래스
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;

    /* comment.
     *  전체 사용자 목록 조회 메서드
     */

    public List<AdminUserListResponseDTO> findAllUsers() {
        return adminUserRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /* comment.
     *  역할별 사용자 목록 조회 메서드
     */

    public List<AdminUserListResponseDTO> findUsersByRole(String role) {
        return adminUserRepository.findByUserRole(role)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /* comment.
     *  엔티티를 목록 화면 DTO로 변환하는 메서드
     */

    private AdminUserListResponseDTO toDTO(AdminUser adminUser) {
        return new AdminUserListResponseDTO(
                adminUser.getUserNo(),
                adminUser.getUserName(),
                adminUser.getUserNickname(),
                adminUser.getUserPhoneNumber(),
                adminUser.getUserRole(),
                adminUser.getUserStatus()
        );
    }

    /* comment.
     *  사용자 계정 비활성화 메서드
     */

    @Transactional
    public void disableUser(Long userNo) {
        AdminUser adminUser = adminUserRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        adminUser.changeUserStatus("INACTIVE");
    }

    /* comment.
     *  사용자 계정 활성화 메서드
     */

    @Transactional
    public void enableUser(Long userNo) {
        AdminUser adminUser = adminUserRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        adminUser.changeUserStatus("ACTIVE");
    }
}