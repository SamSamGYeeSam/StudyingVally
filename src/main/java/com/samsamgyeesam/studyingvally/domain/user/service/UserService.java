package com.samsamgyeesam.studyingvally.domain.user.service;

import com.samsamgyeesam.studyingvally.domain.user.entity.UserUser;
import com.samsamgyeesam.studyingvally.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 관련 비즈니스 로직을 담당하는 서비스이다.
 *
 * 현재는 아이디 찾기 기능을 먼저 담당하고,
 * 추후 회원가입 / 비밀번호 찾기 / 회원정보 수정 등도 이 서비스에서 처리할 수 있다.
 */
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

        /**
         * 이름 또는 전화번호가 비어 있는 경우 예외 처리
         */
        if (userName == null || userName.isBlank() || phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("회원가입 정보를 모두 입력해주세요");
        }

        /**
         * 이름 + 전화번호가 일치하는 사용자를 조회한다.
         */
        UserUser foundUser = userRepository.findByUserNameAndUserPhoneNumber(userName, phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보를 찾을 수 없습니다."));

        /**
         * 조회된 사용자의 아이디를 반환한다.
         */
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
}