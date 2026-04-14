package com.samsamgyeesam.studyingvally.global.Config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 첫 화면 진입을 담당하는 컨트롤러이다.
 *
 * 현재 프로젝트에서는:
 * - "/" 로 들어오면 "/main" 으로 리다이렉트
 * - "/main" 으로 들어오면 main 화면을 반환
 *
 * 이렇게 분리해두면 주소도 깔끔하고,
 * 첫 진입 URL을 명확하게 관리할 수 있다.
 */
@Controller
public class IndexController {

    /**
     * 루트 경로 진입 시 /main 으로 리다이렉트한다.
     *
     * @return redirect:/main
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/main";
    }

    /**
     * 메인 화면을 반환한다.
     *
     * templates/main/main.html 과 연결된다.
     *
     * @return main/main
     */
    @GetMapping("/main")
    public String mainPage() {
        return "main/main";
    }
}