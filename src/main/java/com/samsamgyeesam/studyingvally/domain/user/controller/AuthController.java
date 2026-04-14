package com.samsamgyeesam.studyingvally.domain.user.controller;
// 올리기
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 인증 화면 반환을 담당하는 컨트롤러이다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    /**
     * 로그인 화면 반환
     *
     * 로그인 실패 시 Security에서 /auth/login?error=true 로 보내므로,
     * error 파라미터가 있으면 화면에 에러 문구를 출력할 수 있도록 model에 담아준다.
     *
     * @param error 로그인 실패 여부 파라미터
     * @param model View에 전달할 모델 객체
     * @return auth/login
     */
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {

        /**
         * error 파라미터가 존재하면 화면에서 로그인 실패 문구를 보여줄 수 있도록 설정한다.
         */
        if (error != null) {
            model.addAttribute("loginError", true);
        }

        return "auth/login";
    }

    /**
     * 회원가입 1단계 화면 반환
     *
     * @return auth/signup1
     */
    @GetMapping("/signup1")
    public String signup1() {
        return "auth/signup1";
    }
}