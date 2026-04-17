package com.samsamgyeesam.studyingvally.domain.user.service;

import com.samsamgyeesam.studyingvally.domain.user.dto.DeleteUserDTO;
import com.samsamgyeesam.studyingvally.domain.user.dto.SignupDTO;
import com.samsamgyeesam.studyingvally.domain.user.dto.UserInformationResponseDTO;
import com.samsamgyeesam.studyingvally.domain.user.dto.UserInformationUpdateDTO;
import com.samsamgyeesam.studyingvally.domain.user.entity.UserRole;
import com.samsamgyeesam.studyingvally.domain.user.entity.UserUser;
import com.samsamgyeesam.studyingvally.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 사용자 관련 비즈니스 로직을 담당하는 서비스이다.
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    /**
     * user 테이블 조회용 Repository
     */
    private final UserRepository userRepository;

    /**
     * 이름과 전화번호를 이용해 아이디를 찾는다.
     *
     * @param userName 사용자 이름
     * @param phoneNumber 사용자 전화번호
     * @return 찾은 사용자 아이디
     */
    public String findUserId(String userName, String phoneNumber) {

        // 이름 또는 전화번호가 비어 있는 경우 예외 처리
        if (userName == null || userName.isBlank() || phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("회원가입 정보를 모두 입력해주세요");
        }

        // 이름 + 전화번호가 일치하는 사용자를 조회한다.
        UserUser foundUser = userRepository.findByUserNameAndUserPhoneNumber(userName, phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보를 찾을 수 없습니다."));

        // 조회된 사용자의 아이디를 반환한다.
        return foundUser.getUserId();
    }

    public String findUserPassword(String userId, String phoneNumber) {

        if (userId == null || userId.isBlank() || phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("회원가입 정보를 모두 입력해주세요");
        }

        UserUser user = userRepository
                .findByUserIdAndUserPhoneNumber(userId, phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보를 찾을 수 없습니다."));

        return user.getUserPassword();
    }

    @Transactional
    public void signup(SignupDTO signupDTO) {

        // 필수값 검증
        if (signupDTO.getUserName() == null || signupDTO.getUserName().isBlank()
                || signupDTO.getUserId() == null || signupDTO.getUserId().isBlank()
                || signupDTO.getUserPassword() == null || signupDTO.getUserPassword().isBlank()
                || signupDTO.getUserPhoneNumber() == null || signupDTO.getUserPhoneNumber().isBlank()
                || signupDTO.getUserEmail() == null || signupDTO.getUserEmail().isBlank()
                || signupDTO.getUserNickname() == null || signupDTO.getUserNickname().isBlank()
                || signupDTO.getUserGender() == null || signupDTO.getUserGender().isBlank()) {

            throw new IllegalArgumentException("회원가입 정보를 모두 입력해주세요.");
        }

        // 아이디 중복 검사
        if (userRepository.existsByUserId(signupDTO.getUserId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        /*
         * 회원가입 시작 화면에서 선택한 역할값을
         * String -> Enum 으로 변환한다.
         */
        UserRole selectedRole;

        if ("STUDENT".equals(signupDTO.getUserRole())) {
            selectedRole = UserRole.STUDENT;
        } else if ("TEACHER".equals(signupDTO.getUserRole())) {
            selectedRole = UserRole.TEACHER;
        } else {
            throw new IllegalArgumentException("회원가입 유형을 선택해주세요.");
        }

        /* DTO -> Entity 변환 */
        UserUser newUser = UserUser.builder()
                .userName(signupDTO.getUserName())
                .userId(signupDTO.getUserId())
                .userPassword(signupDTO.getUserPassword())
                .userPhoneNumber(signupDTO.getUserPhoneNumber())
                .userEmail(signupDTO.getUserEmail())
                .userNickname(signupDTO.getUserNickname())
                .userGender(signupDTO.getUserGender())
                .userRole(selectedRole)
                .userStatus("ACTIVE");

        userRepository.save(newUser);
    }

    // 현재 로그인한 사용자 정보 조회
    public UserInformationResponseDTO getUserInformation(String loginUserId) {

        UserUser user = userRepository.findByUserId(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        return new UserInformationResponseDTO(
                user.getUserName(),
                user.getUserPhoneNumber(),
                user.getUserEmail(),
                user.getUserPassword()
        );
    }

    // 현재 로그인한 사용자 정보 수정
    @Transactional
    public void updateUserInformation(String loginUserId, UserInformationUpdateDTO updateDTO) {

        UserUser user = userRepository.findByUserId(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        if (updateDTO.getUserPhoneNumber() == null || updateDTO.getUserPhoneNumber().isBlank()
                || updateDTO.getUserEmail() == null || updateDTO.getUserEmail().isBlank()
                || updateDTO.getUserPassword() == null || updateDTO.getUserPassword().isBlank()) {
            throw new IllegalArgumentException("수정할 정보를 모두 입력해주세요.");
        }

        user.updateInformation(
                updateDTO.getUserPhoneNumber(),
                updateDTO.getUserEmail(),
                updateDTO.getUserPassword()
        );
    }
    /**
     * 현재 로그인한 사용자의 계정을 삭제한다.
     *
     * 탈퇴 전 입력한 비밀번호와
     * 현재 로그인한 사용자 비밀번호가 일치해야 탈퇴 가능하다.
     *
     * @param loginUserId 현재 로그인한 사용자 아이디
     * @param deleteAccountDTO 탈퇴 확인용 비밀번호 DTO
     */
    @Transactional
    public void deleteAccount(String loginUserId, DeleteUserDTO deleteUserDTO) {

        /* 현재 로그인한 사용자 조회 */
        UserUser user = userRepository.findByUserId(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        /* 입력 비밀번호 검증 */
        if (deleteUserDTO.getUserPassword() == null || deleteUserDTO.getUserPassword().isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        /* 현재 프로젝트는 평문 비교 방식 기준 */
        if (!user.getUserPassword().equals(deleteUserDTO.getUserPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        /* 사용자 삭제 */
        userRepository.delete(user);
    }
}