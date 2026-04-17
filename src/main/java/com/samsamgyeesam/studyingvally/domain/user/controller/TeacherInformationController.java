package com.samsamgyeesam.studyingvally.domain.user.controller;

import com.samsamgyeesam.studyingvally.domain.user.dto.UserInformationResponseDTO;
import com.samsamgyeesam.studyingvally.domain.user.dto.UserInformationUpdateDTO;
import com.samsamgyeesam.studyingvally.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TeacherInformationController {
    private final UserService userService;

    /**
     * 강사 정보 조회
     *
     * URL: GET /showinformation
     */
    @GetMapping("/showinformation")
    public String showTeacherInformation(Authentication authentication, Model model) {

        try {
            /* 현재 로그인한 사용자 아이디 추출 */
            String loginUserId = authentication.getName();

            /* 공통 서비스로 현재 사용자 정보 조회 */
            UserInformationResponseDTO userInfo = userService.getUserInformation(loginUserId);
            model.addAttribute("userInfo", userInfo);

            /* 조회 화면에서 수정 버튼 클릭 시 이동할 주소 */
            model.addAttribute("updatePageUrl", "/updateinformation");

            return "auth/showinformation";

        } catch (IllegalArgumentException exception) {
            /* 조회 실패 시 에러 메시지 출력용 */
            model.addAttribute("infoError", exception.getMessage());

            return "auth/showinformation";
        }
    }

    /**
     * 강사 정보 수정 화면 이동
     *
     * URL: GET /updateinformation
     */
    @GetMapping("/updateinformation")
    public String updateTeacherInformationPage(Authentication authentication, Model model) {

        try {
            String loginUserId = authentication.getName();

            UserInformationResponseDTO userInfo = userService.getUserInformation(loginUserId);

            UserInformationUpdateDTO updateDTO = new UserInformationUpdateDTO();
            updateDTO.setUserPhoneNumber(userInfo.getUserPhoneNumber());
            updateDTO.setUserEmail(userInfo.getUserEmail());
            updateDTO.setUserPassword(userInfo.getUserPassword());

            model.addAttribute("userInfo", userInfo);
            model.addAttribute("updateDTO", updateDTO);

            /* 수정 form submit 주소 */
            model.addAttribute("formActionUrl", "/updateinformation");

            /* 뒤로가기 시 조회 화면 주소 */
            model.addAttribute("showPageUrl", "/showinformation");

            return "auth/updateinformation";

        } catch (IllegalArgumentException exception) {
            model.addAttribute("updateError", exception.getMessage());
            model.addAttribute("showPageUrl", "/showinformation");
            model.addAttribute("formActionUrl", "/updateinformation");

            return "auth/updateinformation";
        }
    }

    /**
     * 강사 정보 수정 처리
     *
     * URL: POST /updateinformation
     */
    @PostMapping("/updateinformation")
    public String updateTeacherInformation(Authentication authentication,
                                           @ModelAttribute UserInformationUpdateDTO updateDTO,
                                           Model model) {

        try {
            String loginUserId = authentication.getName();

            userService.updateUserInformation(loginUserId, updateDTO);

            /* 수정 완료 후 다시 조회 화면으로 이동 */
            return "redirect:/showinformation";

        } catch (IllegalArgumentException exception) {
            String loginUserId = authentication.getName();

            UserInformationResponseDTO userInfo = userService.getUserInformation(loginUserId);

            model.addAttribute("userInfo", userInfo);
            model.addAttribute("updateDTO", updateDTO);
            model.addAttribute("updateError", exception.getMessage());

            model.addAttribute("formActionUrl", "/updateinformation");
            model.addAttribute("showPageUrl", "/showinformation");

            return "auth/updateinformation";
        }
    }
}
