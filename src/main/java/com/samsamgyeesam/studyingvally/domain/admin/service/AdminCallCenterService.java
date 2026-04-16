package com.samsamgyeesam.studyingvally.domain.admin.service;

import com.samsamgyeesam.studyingvally.domain.admin.dto.AdminCallCenterMenuDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCallCenterService {

    public List<AdminCallCenterMenuDTO> findAllCallCenterItems() {
        return List.of(
                new AdminCallCenterMenuDTO(
                        1,
                        "공지사항",
                        "공지사항 목록이 뜨며 공지를 추가할 수 있음",
                        "/admin/callcenter/notice"
                ),
                new AdminCallCenterMenuDTO(
                        2,
                        "문의함",
                        "문의함을 누르면 문의 목록을 확인할 수 있음",
                        "/admin/callcenter/contact"
                ),
                new AdminCallCenterMenuDTO(
                        3,
                        "신고함",
                        "공지사항 신고 내역 전체 확인 가능",
                        "/admin/callcenter/report"
                )
        );
    }
}