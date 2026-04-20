package com.samsamgyeesam.studyingvally.domain.user.controller;

import com.samsamgyeesam.studyingvally.domain.user.dto.DeleteUserDTO;
import com.samsamgyeesam.studyingvally.domain.user.dto.UserInformationResponseDTO;
import com.samsamgyeesam.studyingvally.domain.user.dto.UserInformationUpdateDTO;
import com.samsamgyeesam.studyingvally.domain.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/student")
public class StudentInformationController {
    private final UserService userService;

    /*
     * 학생 정보 조회
     * URL: GET /student/home/info
     */
    @GetMapping("/home/info")
    public String showStudentInformation(Authentication authentication, Model model) {

        try {
            String loginUserId = authentication.getName();

            UserInformationResponseDTO userInfo = userService.getUserInformation(loginUserId);
            model.addAttribute("userInfo", userInfo);

            // 조회 화면에서 수정 버튼 클릭 시 이동할 주소
            model.addAttribute("updatePageUrl", "/student/home/update-info");
            // 뒤로가기 누를 시 마이페이지로 이동
            model.addAttribute("confirmPageUrl", "/student/home");

            return "auth/showinformation";

        } catch (IllegalArgumentException exception) {
            model.addAttribute("infoError", exception.getMessage());

            return "auth/showinformation";
        }
    }

    /*
     * 학생 정보 수정 화면 이동
     * URL: GET /student/home/update-info
     */
    @GetMapping("/home/update-info")
    public String updateStudentInformationPage(Authentication authentication, Model model) {

        try {
            String loginUserId = authentication.getName();

            UserInformationResponseDTO userInfo = userService.getUserInformation(loginUserId);

            UserInformationUpdateDTO updateDTO = new UserInformationUpdateDTO();
            updateDTO.setUserPhoneNumber(userInfo.getUserPhoneNumber());
            updateDTO.setUserEmail(userInfo.getUserEmail());
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("updateDTO", updateDTO);

            // 수정 form submit 주소
            model.addAttribute("formActionUrl", "/student/home/update-info");

            // 뒤로가기 시 조회 화면 주소
            model.addAttribute("showPageUrl", "/student/home/info");

            return "auth/updateinformation";

        } catch (IllegalArgumentException exception) {
            model.addAttribute("updateError", exception.getMessage());
            model.addAttribute("formActionUrl", "/student/home/update-info");
            model.addAttribute("showPageUrl", "/student/home/info");

            return "auth/updateinformation";
        }
    }

    /**
     * 학생 정보 수정 처리
     *
     * URL: POST /student/home/update-info
     */
    @PostMapping("/home/update-info")
    public String updateStudentInformation(Authentication authentication,
                                           @ModelAttribute UserInformationUpdateDTO updateDTO,
                                           Model model) {

        try {
            String loginUserId = authentication.getName();

            userService.updateUserInformation(loginUserId, updateDTO);

            return "redirect:/student/home/info";

        } catch (IllegalArgumentException exception) {
            String loginUserId = authentication.getName();

            UserInformationResponseDTO userInfo = userService.getUserInformation(loginUserId);

            model.addAttribute("userInfo", userInfo);
            model.addAttribute("updateDTO", updateDTO);
            model.addAttribute("updateError", exception.getMessage());
            model.addAttribute("formActionUrl", "/student/home/update-info");
            model.addAttribute("showPageUrl", "/student/home/info");

            return "auth/updateinformation";
        }
    }
    /**
     * 학생 탈퇴 화면 이동
     *
     * URL: GET /student/withdraw
     */
    @GetMapping("/withdraw")
    public String deleteStudentAccountPage(Model model) {

        /* 탈퇴 폼 바인딩용 DTO */
        model.addAttribute("deleteUserDTO", new DeleteUserDTO());

        /* 공통 탈퇴 화면에서 사용할 form action 주소 */
        model.addAttribute("formActionUrl", "/student/withdraw");

        return "auth/deleteaccount";
    }

    /**
     * 학생 탈퇴 처리
     *
     * URL: POST /student/withdraw
     */
    @PostMapping("/withdraw")
    public String deleteStudentAccount(Authentication authentication,
                                       @ModelAttribute DeleteUserDTO deleteUserDTO,
                                       Model model,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws ServletException {

        try {
            /* 현재 로그인한 사용자 아이디 추출 */
            String loginUserId = authentication.getName();

            /* 탈퇴 처리 */
            userService.deleteAccount(loginUserId, deleteUserDTO);

            /* 탈퇴 성공 후 로그아웃 처리 */
            request.logout();

            /* 메인 화면으로 이동 */
            return "redirect:/main";

        } catch (IllegalArgumentException exception) {
            /* 실패 시 에러 메시지와 기존 입력값 유지 */
            model.addAttribute("deleteError", exception.getMessage());
            model.addAttribute("deleteUserDTO", deleteUserDTO);
            model.addAttribute("formActionUrl", "/student/withdraw");

            return "auth/deleteaccount";
        }
    }
}
