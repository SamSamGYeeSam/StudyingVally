package com.samsamgyeesam.studyingvally.domain.admin.exception;

/**
 * 관리자 기능에서 사용하는 공통 비즈니스 예외 클래스
 *
 * 왜 필요한가:
 * - 관리자 화면에서 발생하는 입력값 오류, 대상 없음, 중복 처리 등의 예외를
 *   하나의 타입으로 통일하기 위함이다.
 *
 * 사용 예시:
 * - 공지 제목이 비어 있음
 * - 존재하지 않는 문의 상세 조회
 * - 이미 답변 완료된 신고에 다시 답변 시도
 */
public class AdminException extends RuntimeException {

    /**
     * 관리자 예외 생성자
     *
     * @param message 사용자에게 보여줄 예외 메시지
     */
    public AdminException(String message) {
        super(message);
    }
}