package com.samsamgyeesam.studyingvally.domain.user.controller;
// 올리기
import com.samsamgyeesam.studyingvally.domain.user.dto.SignupDTO;
import com.samsamgyeesam.studyingvally.domain.user.service.UserService;
import jakarta.transaction.Transactional;
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
    public String signup1(@RequestParam(required = false) String userRole,
                          Model model) {

        model.addAttribute("userRole", userRole);

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

    // 회원가입 유형 선택 화면 출력
    @GetMapping("/signuptype")
    public String signupType() {
        return "auth/signuptype";
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
    @PostMapping("/signup2")
    public String signup2(@ModelAttribute SignupDTO signupDTO,
                          Model model) {

        /* 1단계 입력값 검증 */
        if (signupDTO.getUserName() == null || signupDTO.getUserName().isBlank()
                || signupDTO.getUserId() == null || signupDTO.getUserId().isBlank()
                || signupDTO.getUserPassword() == null || signupDTO.getUserPassword().isBlank()
                || signupDTO.getUserPhoneNumber() == null || signupDTO.getUserPhoneNumber().isBlank()
                || signupDTO.getUserEmail() == null || signupDTO.getUserEmail().isBlank()) {

            /* 에러 메시지 + 기존 입력값 유지 */
            model.addAttribute("signup1Error", "회원가입 정보를 모두 입력해주세요.");
            model.addAttribute("signupDTO", signupDTO);

            return "auth/signup1";
        }

        /* signup2.html에서 signupDTO.userName 처럼 꺼낼 수 있도록 DTO 자체를 넘긴다 */
        model.addAttribute("signupDTO", signupDTO);

        return "auth/signup2";
    }

    /**
     * 회원가입 최종 저장 처리
     *
     * signup2.html 에서 전달된 회원가입 정보를 받아
     * 서비스 계층에서 실제 DB 저장을 수행한다.
     *
     * 저장 성공 시 첫 화면(/main)으로 리다이렉트한다.
     *
     * @param signupDTO 회원가입 입력값 DTO
     * @param model View에 전달할 모델 객체
     * @return 성공 시 redirect:/main, 실패 시 auth/signup2
     */
    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupDTO signupDTO,
                         Model model) {

        System.out.println("=== AuthController.signup 진입 ===");
        System.out.println("userId = " + signupDTO.getUserId());

        try {
            userService.signup(signupDTO);

            System.out.println("=== 회원가입 성공 후 /main 이동 ===");
            return "redirect:/main";

        } catch (IllegalArgumentException exception) {
            System.out.println("=== IllegalArgumentException 발생 ===");
            exception.printStackTrace();

            model.addAttribute("signup2Error", exception.getMessage());
            model.addAttribute("signupDTO", signupDTO);
            return "auth/signup2";

        } catch (Exception exception) {
            System.out.println("=== 기타 예외 발생 ===");
            exception.printStackTrace();

            model.addAttribute("signup2Error", "회원가입 저장 중 오류가 발생했습니다.");
            model.addAttribute("signupDTO", signupDTO);
            return "auth/signup2";
        }
    }
}