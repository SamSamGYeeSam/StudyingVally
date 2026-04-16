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

        String loginUserId = authentication.getName();

        UserInformationResponseDTO userInfo = userService.getUserInformation(loginUserId);
        model.addAttribute("userInfo", userInfo);

        /* 조회 화면에서 수정 버튼 클릭 시 이동할 주소 */
        model.addAttribute("updatePageUrl", "/updateinformation");

        return "auth/showinformation";
    }

    /**
     * 강사 정보 수정
     *
     * URL: POST /updateinformation
     */
    @GetMapping("/updateinformation")
    public String updateTeacherInformationPage(Authentication authentication, Model model) {

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
    }

    @PostMapping("/updateinformation")
    public String updateTeacherInformation(Authentication authentication,
                                           @ModelAttribute UserInformationUpdateDTO updateDTO,
                                           Model model) {

        String loginUserId = authentication.getName();

        try {
            userService.updateUserInformation(loginUserId, updateDTO);
            return "redirect:/showinformation";

        } catch (IllegalArgumentException exception) {
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
