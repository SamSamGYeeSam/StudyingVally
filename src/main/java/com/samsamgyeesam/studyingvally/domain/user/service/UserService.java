package com.samsamgyeesam.studyingvally.domain.user.service;

import com.samsamgyeesam.studyingvally.domain.user.dto.DeleteUserDTO;
import com.samsamgyeesam.studyingvally.domain.user.dto.SignupDTO;
import com.samsamgyeesam.studyingvally.domain.user.dto.UserInformationResponseDTO;
import com.samsamgyeesam.studyingvally.domain.user.dto.UserInformationUpdateDTO;
import com.samsamgyeesam.studyingvally.domain.user.entity.UserRole;
import com.samsamgyeesam.studyingvally.domain.user.entity.UserUser;
import com.samsamgyeesam.studyingvally.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 관련 비즈니스 로직 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    /**
     * user 테이블 조회용 Repository
     */
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 이름 + 전화번호로 아이디 찾기
     */
    public String findUserId(String userName, String phoneNumber) {

        if (userName == null || userName.isBlank()
                || phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("회원가입 정보를 모두 입력해주세요");
        }

        UserUser foundUser = userRepository.findByUserNameAndUserPhoneNumber(userName, phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보를 찾을 수 없습니다."));

        return foundUser.getUserId();
    }

    public void validateUserForPasswordReset(String userId, String phoneNumber) {
        if (userId == null || userId.isBlank()
                || phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("아이디와 전화번호를 모두 입력해주세요.");
        }

        userRepository.findByUserIdAndUserPhoneNumber(userId, phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보를 찾을 수 없습니다."));
    }


    @Transactional
    public void resetUserPassword(String userId, String phoneNumber, String newPassword) {
        if (userId == null || userId.isBlank()
                || phoneNumber == null || phoneNumber.isBlank()
                || newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("모든 정보를 입력해주세요.");
        }

        UserUser user = userRepository.findByUserIdAndUserPhoneNumber(userId, phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보를 찾을 수 없습니다."));

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.updatePassword(encodedPassword);
    }

    /**
     * 회원가입
     */
    @Transactional
    public void signup(SignupDTO signupDTO) {

        /* 필수값 검증 */
        if (signupDTO.getUserName() == null || signupDTO.getUserName().isBlank()
                || signupDTO.getUserId() == null || signupDTO.getUserId().isBlank()
                || signupDTO.getUserPassword() == null || signupDTO.getUserPassword().isBlank()
                || signupDTO.getUserPhoneNumber() == null || signupDTO.getUserPhoneNumber().isBlank()
                || signupDTO.getUserEmail() == null || signupDTO.getUserEmail().isBlank()
                || signupDTO.getUserNickname() == null || signupDTO.getUserNickname().isBlank()
                || signupDTO.getUserGender() == null || signupDTO.getUserGender().isBlank()) {

            throw new IllegalArgumentException("회원가입 정보를 모두 입력해주세요.");
        }

        /* 이메일 형식 검증
         * 조건:
         * - gmail.com만 허용
         * - @ 앞은 영문/숫자만 허용
         */
        if (!isValidEmailFormat(signupDTO.getUserEmail())) {
            throw new IllegalArgumentException("gmail 형식의 이메일만 입력 가능합니다.");
        }

        /* 아이디 중복 검사 */
        if (userRepository.existsByUserId(signupDTO.getUserId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        /* 이메일 중복 검사 */
        if (userRepository.existsByUserEmail(signupDTO.getUserEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        /* 전화번호 중복 검사 */
        if (userRepository.existsByUserPhoneNumber(signupDTO.getUserPhoneNumber())) {
            throw new IllegalArgumentException("이미 사용 중인 전화번호입니다.");
        }

        /* 닉네임 중복 검사 */
        if (userRepository.existsByUserNickname(signupDTO.getUserNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        /*
         * 회원가입 시작 화면에서 선택한 역할값을
         * String -> Enum 으로 변환
         */
        UserRole selectedRole;

        if ("STUDENT".equals(signupDTO.getUserRole())) {
            selectedRole = UserRole.STUDENT;
        } else if ("TEACHER".equals(signupDTO.getUserRole())) {
            selectedRole = UserRole.TEACHER;
        } else {
            throw new IllegalArgumentException("회원가입 유형을 선택해주세요.");
        }

        String encodedPassword = passwordEncoder.encode(signupDTO.getUserPassword());

        /* DTO -> Entity 변환 */
        UserUser newUser = UserUser.builder()
                .userName(signupDTO.getUserName())
                .userId(signupDTO.getUserId())
                .userPassword(encodedPassword)
                .userPhoneNumber(signupDTO.getUserPhoneNumber())
                .userEmail(signupDTO.getUserEmail())
                .userNickname(signupDTO.getUserNickname())
                .userGender(signupDTO.getUserGender())
                .userRole(selectedRole)
                .userStatus("ACTIVE")
                .loginFailCount(0)
                .accountLocked(false);

        userRepository.save(newUser);
    }

    /**
     * 현재 로그인한 사용자 정보 조회
     */
    public UserInformationResponseDTO getUserInformation(String loginUserId) {

        UserUser user = userRepository.findByUserId(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        return new UserInformationResponseDTO(
                user.getUserName(),
                user.getUserPhoneNumber(),
                user.getUserEmail()
        );
    }

    /**
     * 현재 로그인한 사용자 정보 수정
     */
    @Transactional
    public void updateUserInformation(String loginUserId, UserInformationUpdateDTO updateDTO) {

        String encodedPassword = passwordEncoder.encode(updateDTO.getUserPassword());

        UserUser user = userRepository.findByUserId(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        if (updateDTO.getUserPhoneNumber() == null || updateDTO.getUserPhoneNumber().isBlank()
                || updateDTO.getUserEmail() == null || updateDTO.getUserEmail().isBlank()
                || updateDTO.getUserPassword() == null || updateDTO.getUserPassword().isBlank()) {
            throw new IllegalArgumentException("수정할 정보를 모두 입력해주세요.");
        }

        /* 이메일 형식 검증 */
        if (!isValidEmailFormat(updateDTO.getUserEmail())) {
            throw new IllegalArgumentException("gmail 형식의 이메일만 입력 가능합니다.");
        }

        user.updateInformation(
                updateDTO.getUserPhoneNumber(),
                updateDTO.getUserEmail(),
                encodedPassword
        );
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void deleteAccount(String loginUserId, DeleteUserDTO deleteUserDTO) {

        UserUser user = userRepository.findByUserId(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        if (deleteUserDTO.getUserPassword() == null || deleteUserDTO.getUserPassword().isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        if (!passwordEncoder.matches(deleteUserDTO.getUserPassword(), user.getUserPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);
    }

    /**
     * 내 정보 조회/수정 전 비밀번호 확인
     */
    public void verifyUserPassword(String loginUserId, String userPassword) {

        UserUser user = userRepository.findByUserId(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        if (userPassword == null || userPassword.isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        if (!passwordEncoder.matches(userPassword, user.getUserPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * 로그인 실패 횟수 증가
     *
     * 5회 이상이면 계정 잠금
     */
    @Transactional
    public void incrementLoginFailCount(String loginId) {

        UserUser user = userRepository.findByUserId(loginId).orElse(null);

        /* 존재하지 않는 아이디면 종료 */
        if (user == null) {
            return;
        }

        /* 이미 잠긴 계정이면 종료 */
        if (user.isAccountLocked()) {
            return;
        }

        user.increaseLoginFailCount();

        if (user.getLoginFailCount() >= 5) {
            user.lockAccount();
        }
    }

    /**
     * 로그인 성공 시 실패 횟수 초기화
     */
    @Transactional
    public void resetLoginFailCount(String loginId) {

        UserUser user = userRepository.findByUserId(loginId).orElse(null);

        if (user == null) {
            return;
        }

        user.resetLoginFailCount();
    }

    /*
     * 이메일 형식 검증 메서드
     *
     * 조건:
     * - gmail.com만 허용
     * - @ 앞은 영문 대소문자와 숫자만 허용
     * - . _ - 같은 특수문자는 허용하지 않음
     */
    private boolean isValidEmailFormat(String email) {
        return email != null
                && email.matches("^[A-Za-z0-9]+@gmail\\.com$");
    }
}