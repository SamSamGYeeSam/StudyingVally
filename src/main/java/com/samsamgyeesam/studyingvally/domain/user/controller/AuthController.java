package com.samsamgyeesam.studyingvally.domain.user.controller;

import com.samsamgyeesam.studyingvally.domain.user.dto.SignupDTO;
import com.samsamgyeesam.studyingvally.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 인증/회원가입/아이디 찾기/비밀번호 재설정 관련 요청을 처리하는 컨트롤러
 *
 * 추가된 핵심 로직:
 * - 이미 로그인한 사용자가 /auth 하위 공개 페이지로 다시 접근하면
 *   역할에 맞는 메인 페이지로 강제 이동시킨다.
 * - 학생  -> /student/main
 * - 강사  -> /teacher/teachermain
 * - 관리자 -> /admin/main
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    /**
     * 회원 관련 비즈니스 로직 서비스
     */
    private final UserService userService;

    /**
     * 로그인 화면
     *
     * 이미 로그인한 상태면 로그인 화면을 다시 보여주지 않고
     * 역할별 메인 화면으로 보낸다.
     */
    @GetMapping("/login")
    public String login(Authentication authentication, Model model, HttpSession session) {

        String redirectUrl = getRedirectUrlByRole(authentication);
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }

        /*
         * 로그인 실패 메시지는 AuthFailHandler가 session에 저장해둔 값을 꺼내서 사용한다.
         * 한 번 보여준 뒤에는 제거한다.
         */
        String loginErrorMessage = (String) session.getAttribute("loginErrorMessage");

        if (loginErrorMessage != null && !loginErrorMessage.isBlank()) {
            model.addAttribute("loginError", true);
            model.addAttribute("loginErrorMessage", loginErrorMessage);
            session.removeAttribute("loginErrorMessage");
        }

        return "auth/login";
    }

    /**
     * 로그인 에러 재진입용 URL
     *
     * 실제 처리는 login()과 동일하게 맞춘다.
     */
    @GetMapping("/loginerror")
    public String loginError(Authentication authentication, Model model, HttpSession session) {
        return login(authentication, model, session);
    }

    /**
     * 회원가입 유형 선택 화면
     *
     * 이미 로그인했으면 접근 차단 후 역할별 메인으로 이동
     */
    @GetMapping("/signuptype")
    public String signupType(Authentication authentication) {

        String redirectUrl = getRedirectUrlByRole(authentication);
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }

        return "auth/signuptype";
    }

    /**
     * 회원가입 1단계 화면
     *
     * userRole(STUDENT / TEACHER)을 받아서 화면에 전달
     * 이미 로그인했으면 접근 차단
     */
    @GetMapping("/signup1")
    public String signup1(Authentication authentication,
                          @RequestParam(required = false) String userRole,
                          Model model) {

        String redirectUrl = getRedirectUrlByRole(authentication);
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }

        model.addAttribute("userRole", userRole);
        return "auth/signup1";
    }

    /**
     * 회원가입 2단계 화면으로 이동하기 전 1차 입력값 검증
     *
     * 주의:
     * 이 단계는 최종 저장이 아니라 화면 이동 전 검증이다.
     */
    @PostMapping("/signup2")
    public String signup2(Authentication authentication,
                          @ModelAttribute SignupDTO signupDTO,
                          Model model) {

        String redirectUrl = getRedirectUrlByRole(authentication);
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }

        if (signupDTO.getUserName() == null || signupDTO.getUserName().isBlank()
                || signupDTO.getUserId() == null || signupDTO.getUserId().isBlank()
                || signupDTO.getUserPassword() == null || signupDTO.getUserPassword().isBlank()
                || signupDTO.getUserPhoneNumber() == null || signupDTO.getUserPhoneNumber().isBlank()
                || signupDTO.getUserEmail() == null || signupDTO.getUserEmail().isBlank()) {

            model.addAttribute("signup1Error", "회원가입 정보를 모두 입력해주세요.");
            model.addAttribute("signupDTO", signupDTO);
            return "auth/signup1";
        }

        model.addAttribute("signupDTO", signupDTO);
        return "auth/signup2";
    }

    /**
     * 최종 회원가입 처리
     *
     * 이미 로그인 상태면 회원가입 페이지 접근 자체를 막는다.
     */
    @PostMapping("/signup")
    public String signup(Authentication authentication,
                         @ModelAttribute SignupDTO signupDTO,
                         Model model) {

        String redirectUrl = getRedirectUrlByRole(authentication);
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }

        try {
            userService.signup(signupDTO);
            return "redirect:/main";

        } catch (IllegalArgumentException exception) {
            model.addAttribute("signup2Error", exception.getMessage());
            model.addAttribute("signupDTO", signupDTO);
            return "auth/signup2";
        }
    }

    /**
     * 아이디/비밀번호 찾기 선택 화면
     */
    @GetMapping("/find")
    public String find(Authentication authentication) {

        String redirectUrl = getRedirectUrlByRole(authentication);
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }

        return "auth/find";
    }

    /**
     * 아이디 찾기 화면
     */
    @GetMapping("/findid")
    public String findId(Authentication authentication) {

        String redirectUrl = getRedirectUrlByRole(authentication);
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }

        return "auth/findid";
    }

    /**
     * 아이디 찾기 결과 처리
     *
     * 이름 + 전화번호로 사용자 아이디를 조회한다.
     */
    @GetMapping("/findid2")
    public String findIdResult(Authentication authentication,
                               @RequestParam(required = false) String userName,
                               @RequestParam(required = false) String phoneNumber,
                               Model model) {

        String redirectUrl = getRedirectUrlByRole(authentication);
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }

        try {
            String foundUserId = userService.findUserId(userName, phoneNumber);
            model.addAttribute("foundUserId", foundUserId);
            return "auth/findid2";

        } catch (IllegalArgumentException exception) {
            model.addAttribute("findIdError", exception.getMessage());
            return "auth/findid";
        }
    }

    /**
     * 비밀번호 재설정 1단계 화면
     *
     * 아이디 + 전화번호 본인 확인 입력 화면
     */
    @GetMapping("/findpw1")
    public String findPw(Authentication authentication) {

        String redirectUrl = getRedirectUrlByRole(authentication);
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }

        return "auth/findpw1";
    }

    /**
     * 비밀번호 재설정 2단계 진입 전 본인 확인
     *
     * userId + phoneNumber 조합이 맞는 사용자만
     * 실제 비밀번호 재설정 화면(findpw2)으로 이동시킨다.
     */
    @PostMapping("/findpw2")
    public String verifyUserForPasswordReset(Authentication authentication,
                                             @RequestParam String userId,
                                             @RequestParam String phoneNumber,
                                             Model model) {

        String redirectUrl = getRedirectUrlByRole(authentication);
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }

        try {
            userService.validateUserForPasswordReset(userId, phoneNumber);
            model.addAttribute("userId", userId);
            model.addAttribute("phoneNumber", phoneNumber);
            return "auth/findpw2";

        } catch (IllegalArgumentException exception) {
            model.addAttribute("findPwError", exception.getMessage());
            return "auth/findpw1";
        }
    }

    /**
     * 새 비밀번호 저장 처리
     *
     * 1. 새 비밀번호 입력 여부 확인
     * 2. 새 비밀번호 / 확인 비밀번호 일치 여부 확인
     * 3. 서비스에서 BCrypt 해시로 저장
     */
    @PostMapping("/resetpw")
    public String resetPassword(Authentication authentication,
                                @RequestParam String userId,
                                @RequestParam String phoneNumber,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                Model model) {

        String redirectUrl = getRedirectUrlByRole(authentication);
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }

        try {
            if (newPassword == null || newPassword.isBlank()
                    || confirmPassword == null || confirmPassword.isBlank()) {
                throw new IllegalArgumentException("새 비밀번호를 모두 입력해주세요.");
            }

            if (!newPassword.equals(confirmPassword)) {
                throw new IllegalArgumentException("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            }

            userService.resetUserPassword(userId, phoneNumber, newPassword);
            return "redirect:/auth/login";

        } catch (IllegalArgumentException exception) {
            model.addAttribute("resetPwError", exception.getMessage());
            model.addAttribute("userId", userId);
            model.addAttribute("phoneNumber", phoneNumber);
            return "auth/findpw2";
        }
    }

    /**
     * 현재 인증 정보(authentication)를 보고
     * 로그인 상태면 역할별 메인 페이지 URL을 반환한다.
     *
     * 반환값:
     * - 학생   -> /student/main
     * - 강사   -> /teacher/teachermain
     * - 관리자 -> /admin/main
     * - 비로그인 -> null
     */
    private String getRedirectUrlByRole(Authentication authentication) {

        /*
         * 아래 경우는 로그인 상태가 아니라고 본다.
         * 1. authentication 자체가 없음
         * 2. 인증 완료 상태가 아님
         * 3. anonymousUser(익명 사용자)
         */
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getName())) {
            return null;
        }

        if (hasRole(authentication, "ROLE_STUDENT")) {
            return "/student/main";
        }

        if (hasRole(authentication, "ROLE_TEACHER")) {
            return "/teacher/teachermain";
        }

        if (hasRole(authentication, "ROLE_ADMIN")) {
            return "/admin/main";
        }

        return null;
    }

    /**
     * 현재 인증 정보가 특정 권한을 가지고 있는지 확인하는 공통 메서드
     *
     * 예:
     * - ROLE_STUDENT
     * - ROLE_TEACHER
     * - ROLE_ADMIN
     */
    private boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }
}