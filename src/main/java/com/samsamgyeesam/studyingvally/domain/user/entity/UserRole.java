package com.samsamgyeesam.studyingvally.domain.user.entity;

/**
 * user 테이블의 user_role 컬럼과 매핑되는 enum이다.
 *
 * 현재 DB 기준 값:
 * - STUDENT
 * - TEACHER
 */
public enum UserRole {
    STUDENT,
    TEACHER
}