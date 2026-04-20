package com.samsamgyeesam.studyingvally.domain.user.controller;

import com.samsamgyeesam.studyingvally.domain.user.dto.DeleteUserDTO;
import com.samsamgyeesam.studyingvally.domain.user.dto.UserInformationResponseDTO;
import com.samsamgyeesam.studyingvally.domain.user.dto.UserInformationUpdateDTO;
import com.samsamgyeesam.studyingvally.domain.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    public String showTeacherInformation(Authentication authentication, HttpSession session  ,Model model) {

        try {
            /* 비밀번호 확인 없이 직접 접근하는 경우 차단 */
            Boolean verified = (Boolean) session.getAttribute("teacherInfoVerified");

            if (verified == null || !verified) {
                model.addAttribute("passwordCheckError", "비밀번호 확인 후 접근할 수 있습니다.");
                return "course/mypage";
            }
            /* 현재 로그인한 사용자 아이디 추출 */
            String loginUserId = authentication.getName();

            /* 공통 서비스로 현재 사용자 정보 조회 */
            UserInformationResponseDTO userInfo = userService.getUserInformation(loginUserId);
            model.addAttribute("userInfo", userInfo);

            /* 조회 화면에서 수정 버튼 클릭 시 이동할 주소 */
            model.addAttribute("updatePageUrl", "/updateinformation");
            /* 확인 누를 시 마이페이지로 이동*/
            model.addAttribute("confirmPageUrl", "/teacher/mypage");

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
    public String updateTeacherInformationPage(Authentication authentication, HttpSession session, Model model) {

        try {
            /* 비밀번호 확인 없이 직접 접근하는 경우 차단 */
            Boolean verified = (Boolean) session.getAttribute("teacherInfoVerified");

            if (verified == null || !verified) {
                model.addAttribute("passwordCheckError", "비밀번호 확인 후 접근할 수 있습니다.");
                return "course/mypage";
            }

            String loginUserId = authentication.getName();

            UserInformationResponseDTO userInfo = userService.getUserInformation(loginUserId);

            UserInformationUpdateDTO updateDTO = new UserInformationUpdateDTO();
            updateDTO.setUserPhoneNumber(userInfo.getUserPhoneNumber());
            updateDTO.setUserEmail(userInfo.getUserEmail());

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
    /**
     * 강사 탈퇴 화면 이동
     *
     * URL: GET /deleteaccount
     */
    @GetMapping("/deleteaccount")
    public String deleteTeacherAccountPage(Model model) {

        /* 탈퇴 폼 바인딩용 DTO */
        model.addAttribute("deleteUserDTO", new DeleteUserDTO());

        /* 공통 탈퇴 화면에서 사용할 form action 주소 */
        model.addAttribute("formActionUrl", "/deleteaccount");

        return "auth/deleteaccount";
    }

    /**
     * 강사 탈퇴 처리
     *
     * URL: POST /deleteaccount
     */
    @PostMapping("/deleteaccount")
    public String deleteTeacherAccount(Authentication authentication,
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
            model.addAttribute("formActionUrl", "/deleteaccount");

            return "auth/deleteaccount";
        }
    }

    /**
     * 강사 내 정보 조회 전 비밀번호 확인 처리
     * 비밀번호 검증이 성공하면 세션에 확인 완료 상태를 저장한 뒤
     * 실제 조회 화면(/showinformation)으로 리다이렉트한다.
     */
    @PostMapping("/showinformation/check-password")
    public String checkTeacherInfoPassword(Authentication authentication,
                                           @ModelAttribute DeleteUserDTO deleteUserDTO,
                                           HttpSession session,
                                           Model model) {

        try {
            /* 현재 로그인한 사용자 아이디 */
            String loginUserId = authentication.getName();

            /* 비밀번호 검증 */
            userService.verifyUserPassword(loginUserId, deleteUserDTO.getUserPassword());

            /* 검증 성공 시 세션에 인증 완료 상태 저장 */
            session.setAttribute("teacherInfoVerified", true);

            /* 실제 조회 페이지로 이동 */
            return "redirect:/showinformation";

        } catch (IllegalArgumentException exception) {
            /* 실패 시 마이페이지로 다시 이동하면서 에러 메시지 전달 */
            model.addAttribute("passwordCheckError", exception.getMessage());

            return "course/mypage";
        }
    }
}
