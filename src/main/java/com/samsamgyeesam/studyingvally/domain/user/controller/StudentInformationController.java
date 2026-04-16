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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student/home")
public class StudentInformationController {
    private final UserService userService;

    /*
     * 학생 정보 조회
     */
    @GetMapping("/info")
    public String showStudentInformation(Authentication authentication, Model model) {

        String loginUserId = authentication.getName();

        UserInformationResponseDTO userInfo = userService.getUserInformation(loginUserId);
        model.addAttribute("userInfo", userInfo);

        /* 조회 화면에서 수정 버튼 클릭 시 이동할 주소 */
        model.addAttribute("updatePageUrl", "/student/home/update-info");

        return "auth/showinformation";
    }

    /**
     * 학생 정보 수정
     *
     * URL: POST /student/home/update-info
     */
    @GetMapping("/update-info")
    public String updateStudentInformationPage(Authentication authentication, Model model) {

        String loginUserId = authentication.getName();

        UserInformationResponseDTO userInfo = userService.getUserInformation(loginUserId);

        UserInformationUpdateDTO updateDTO = new UserInformationUpdateDTO();
        updateDTO.setUserPhoneNumber(userInfo.getUserPhoneNumber());
        updateDTO.setUserEmail(userInfo.getUserEmail());
        updateDTO.setUserPassword(userInfo.getUserPassword());

        model.addAttribute("userInfo", userInfo);
        model.addAttribute("updateDTO", updateDTO);

        /* 수정 form submit 주소 */
        model.addAttribute("formActionUrl", "/student/home/update-info");

        /* 뒤로가기 시 조회 화면 주소 */
        model.addAttribute("showPageUrl", "/student/home/info");

        return "auth/updateinformation";
    }

    @PostMapping("/update-info")
    public String updateStudentInformation(Authentication authentication,
                                           @ModelAttribute UserInformationUpdateDTO updateDTO,
                                           Model model) {

        String loginUserId = authentication.getName();

        try {
            userService.updateUserInformation(loginUserId, updateDTO);
            return "redirect:/student/home/info";

        } catch (IllegalArgumentException exception) {
            UserInformationResponseDTO userInfo = userService.getUserInformation(loginUserId);

            model.addAttribute("userInfo", userInfo);
            model.addAttribute("updateDTO", updateDTO);
            model.addAttribute("updateError", exception.getMessage());

            model.addAttribute("formActionUrl", "/student/home/update-info");
            model.addAttribute("showPageUrl", "/student/home/info");

            return "auth/updateinformation";
        }
    }
}
