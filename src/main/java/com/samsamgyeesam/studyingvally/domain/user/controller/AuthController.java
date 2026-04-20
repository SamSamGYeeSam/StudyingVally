package com.samsamgyeesam.studyingvally.domain.user.controller;

import com.samsamgyeesam.studyingvally.domain.user.dto.SignupDTO;
import com.samsamgyeesam.studyingvally.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        String loginErrorMessage = (String) session.getAttribute("loginErrorMessage");

        if (loginErrorMessage != null && !loginErrorMessage.isBlank()) {
            model.addAttribute("loginError", true);
            model.addAttribute("loginErrorMessage", loginErrorMessage);
            session.removeAttribute("loginErrorMessage");
        }

        return "auth/login";
    }

    @GetMapping("/loginerror")
    public String loginError(Model model, HttpSession session) {
        return login(model, session);
    }

    @GetMapping("/signuptype")
    public String signupType() {
        return "auth/signuptype";
    }

    @GetMapping("/signup1")
    public String signup1(@RequestParam(required = false) String userRole,
                          Model model) {

        model.addAttribute("userRole", userRole);

        return "auth/signup1";
    }

    @PostMapping("/signup2")
    public String signup2(@ModelAttribute SignupDTO signupDTO,
                          Model model) {

        if (signupDTO.getUserName() == null || signupDTO.getUserName().isBlank()
                || signupDTO.getUserId() == null || signupDTO.getUserId().isBlank()
                || signupDTO.getUserPassword() == null || signupDTO.getUserPassword().isBlank()
                || signupDTO.getUserPhoneNumber() == null || signupDTO.getUserPhoneNumber().isBlank()
                || signupDTO.getUserEmail() == null || signupDTO.getUserEmail().isBlank()) {

            model.addAttribute("signup1Error", "?뚯썝媛???뺣낫瑜?紐⑤몢 ?낅젰?댁＜?몄슂.");
            model.addAttribute("signupDTO", signupDTO);

            return "auth/signup1";
        }

        model.addAttribute("signupDTO", signupDTO);

        return "auth/signup2";
    }

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

    @PostMapping("/findpw2")
    public String verifyUserForPasswordReset(@RequestParam String userId,
                                             @RequestParam String phoneNumber,
                                             Model model) {
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

    @PostMapping("/resetpw")
    public String resetPassword(@RequestParam String userId,
                                @RequestParam String phoneNumber,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                Model model) {
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
}
