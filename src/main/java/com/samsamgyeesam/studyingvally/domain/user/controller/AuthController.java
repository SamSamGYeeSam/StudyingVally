package com.samsamgyeesam.studyingvally.domain.user.controller;

import com.samsamgyeesam.studyingvally.domain.user.dto.SignupDTO;
import com.samsamgyeesam.studyingvally.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 인증 화면 반환을 담당하는 컨트롤러이다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    /* 사용자 비즈니스 로직 서비스 */
    private final UserService userService;

    /**
     * 로그인 화면 반환
     *
     * 로그인 실패 시 AuthFailHandler에서
     * /auth/login?error=true&message=... 형태로 보내므로,
     * error와 message를 받아 화면에 전달한다.
     *
     * @param error 로그인 실패 여부
     * @param message 로그인 실패 메시지
     * @param model View에 전달할 모델 객체
     * @return auth/login
     */
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String message,
                        Model model) {

        /* 로그인 실패 여부 전달 */
        if (error != null) {
            model.addAttribute("loginError", true);
        }

        /* 실패 메시지가 있으면 화면에 전달 */
        if (message != null && !message.isBlank()) {
            model.addAttribute("loginErrorMessage", message);
        }

        return "auth/login";
    }

    /**
     * 회원가입 유형 선택 화면 반환
     */
    @GetMapping("/signup-type")
    public String signupType() {
        return "auth/signup-type";
    }

    /**
     * 회원가입 1단계 화면 반환
     */
    @GetMapping("/signup1")
    public String signup1(@RequestParam(required = false) String userRole,
                          Model model) {

        model.addAttribute("userRole", userRole);

        return "auth/signup1";
    }

    /**
     * 회원가입 2단계 화면 이동
     */
    @PostMapping("/signup2")
    public String signup2(@ModelAttribute SignupDTO signupDTO,
                          Model model) {

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
     * 회원가입 최종 처리
     */
    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupDTO signupDTO,
                         Model model) {

        try {
            userService.signup(signupDTO);
            return "redirect:/main";

        } catch (IllegalArgumentException exception) {
            model.addAttribute("signup2Error", exception.getMessage());
            model.addAttribute("signupDTO", signupDTO);
            return "auth/signup2";
        }
    }

    @GetMapping("/find")
    public String find() {
        return "auth/find";
    }

    @GetMapping("/findid")
    public String findId() {
        return "auth/findid";
    }

    @GetMapping("/findid2")
    public String findIdResult(@RequestParam(required = false) String userName,
                               @RequestParam(required = false) String phoneNumber,
                               Model model) {

        try {
            String foundUserId = userService.findUserId(userName, phoneNumber);
            model.addAttribute("foundUserId", foundUserId);

            return "auth/findid2";

        } catch (IllegalArgumentException exception) {
            model.addAttribute("findIdError", exception.getMessage());
            return "auth/findid";
        }
    }

    @GetMapping("/findpw1")
    public String findPw() {
        return "auth/findpw1";
    }

    @GetMapping("/findpw2")
    public String findPwResult(@RequestParam(required = false) String userId,
                               @RequestParam(required = false) String phoneNumber,
                               Model model) {

        try {
            String foundPassword = userService.findUserPassword(userId, phoneNumber);
            model.addAttribute("foundPassword", foundPassword);

            return "auth/findpw2";

        } catch (IllegalArgumentException exception) {
            model.addAttribute("findPwError", exception.getMessage());
            return "auth/findpw1";
        }
    }
}