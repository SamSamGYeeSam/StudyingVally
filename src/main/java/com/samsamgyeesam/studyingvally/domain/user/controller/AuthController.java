package com.samsamgyeesam.studyingvally.domain.user.controller;
// 올리기
import com.samsamgyeesam.studyingvally.domain.user.service.UserService;
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

    // 사용자 비즈니스 로직 서비스
    private final UserService userService;

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

    /*
    * 아이디/비밀번호 찾기 반환
    * 아이디/비밀번호를 잊으셨나요? 버튼을 누르게 되면 아이디/비밀번호 찾기 화면으로 이동
    * */
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
            /**
             * 실제 서비스 로직을 호출해 아이디를 찾는다.
             */
            String foundUserId = userService.findUserId(userName, phoneNumber);

            /**
             * 찾은 아이디를 결과 화면에 전달한다.
             */
            model.addAttribute("foundUserId", foundUserId);

            return "auth/findid2";

        } catch (IllegalArgumentException exception) {
            /**
             * 실패 시 다시 입력 화면으로 보내고 에러 메시지를 전달한다.
             */
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

    /*
     * 회원가입 2단계 화면 반환
     *
     * 회원가입 1단계에서 입력한 값을 받아
     * 다음 단계 화면으로 전달한다.
     *
     * @param userName 사용자 이름
     * @param userId 사용자 아이디
     * @param userPassword 사용자 비밀번호
     * @param userNickname 사용자 닉네임
     * @param userPhoneNumber 사용자 전화번호
     * @param userEmail 사용자 이메일
     * @param model View에 전달할 모델 객체
     * @return auth/signup2
     */
    @GetMapping("/signup2")
    public String signup2(@RequestParam(required = false) String userName,
                          @RequestParam(required = false) String userId,
                          @RequestParam(required = false) String userPassword,
                          @RequestParam(required = false) String userNickname,
                          @RequestParam(required = false) String userPhoneNumber,
                          @RequestParam(required = false) String userEmail,
                          Model model) {

        /**
         * 회원가입 1단계 입력값을 2단계 화면으로 전달한다.
         */
        model.addAttribute("userName", userName);
        model.addAttribute("userId", userId);
        model.addAttribute("userPassword", userPassword);
        model.addAttribute("userNickname", userNickname);
        model.addAttribute("userPhoneNumber", userPhoneNumber);
        model.addAttribute("userEmail", userEmail);

        return "auth/signup2";
    }
}