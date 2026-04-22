use studyingvally;

SET FOREIGN_KEY_CHECKS = 0; -- 외래키 제약조건 무시 (안전한 초기화를 위해)

-- ==========================================
-- [1] 테이블 생성 스크립트
-- ==========================================

-- 1. admin 테이블
DROP TABLE IF EXISTS admin;
CREATE TABLE admin (
                       admin_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '관리자 번호',
                       admin_id VARCHAR(50) NOT NULL COMMENT '아이디',
                       admin_password VARCHAR(255) NOT NULL COMMENT '비밀번호',
                       PRIMARY KEY (admin_no)
) COMMENT = '관리자 정보';

-- 2. user 테이블
DROP TABLE IF EXISTS user;
CREATE TABLE user (
                      user_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '유저번호',
                      user_id VARCHAR(50) NOT NULL COMMENT '아이디',
                      user_password VARCHAR(255) NOT NULL COMMENT '비밀번호',
                      user_phone_number VARCHAR(20) NOT NULL COMMENT '전화번호',
                      user_email VARCHAR(100) NOT NULL COMMENT '이메일',
                      user_role VARCHAR(20) NOT NULL COMMENT '유저역할 (예: STUDENT, TEACHER)',
                      user_nickname VARCHAR(50) NOT NULL COMMENT '닉네임',
                      user_name VARCHAR(50) NOT NULL COMMENT '이름',
                      user_status VARCHAR(20) NOT NULL COMMENT '계정상태 (예: ACTIVE, INACTIVE)',
                      user_gender VARCHAR(10) NOT NULL COMMENT '성별',
                      PRIMARY KEY (user_no)
) COMMENT = '사용자 정보';

-- 3. course 테이블
DROP TABLE IF EXISTS course;
CREATE TABLE course (
                        course_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '강의번호',
                        course_title VARCHAR(255) NOT NULL COMMENT '강의제목',
                        course_description TEXT NOT NULL COMMENT '강의 설명',
                        course_created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '개설시간',
                        course_status VARCHAR(20) NOT NULL COMMENT '강의 오픈 여부 (예: OPEN, CLOSED)',
                        course_send_approve TINYINT(1) NOT NULL COMMENT '승인요청여부 (0: 미요청, 1: 요청)',
                        user_no BIGINT COMMENT '유저번호 (강사)',
                        created_date DATETIME COMMENT '생성일시',
                        modified_date DATETIME COMMENT '수정일시',
                        PRIMARY KEY (course_id)
) COMMENT = '강의 정보';

-- 4. chapter 테이블
DROP TABLE IF EXISTS chapter;
CREATE TABLE chapter (
                         chap_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '챕터번호',
                         chap_title VARCHAR(255) NOT NULL COMMENT '챕터제목',
                         chap_desc TEXT NOT NULL COMMENT '챕터 설명',
                         chap_url VARCHAR(255) NOT NULL COMMENT '첨부파일/영상 경로',
                         course_id BIGINT COMMENT '강의번호',
                         PRIMARY KEY (chap_no)
) COMMENT = '챕터 정보';

-- 5. chapter_attempt 테이블
DROP TABLE IF EXISTS chapter_attempt;
CREATE TABLE chapter_attempt (
                                 chapter_attempt_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '챕터수강아이디',
                                 user_no BIGINT COMMENT '유저번호',
                                 chap_no BIGINT COMMENT '챕터번호',
                                 PRIMARY KEY (chapter_attempt_id)
) COMMENT = '챕터 수강 기록';

-- 6. course_notice 테이블
DROP TABLE IF EXISTS course_notice;
CREATE TABLE course_notice (
                               course_notice_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '강의공지번호',
                               course_notice_title VARCHAR(255) NOT NULL COMMENT '강의 제목',
                               course_notice_desc TEXT NOT NULL COMMENT '강의 내용',
                               user_no BIGINT COMMENT '유저번호',
                               course_id BIGINT COMMENT '강의번호',
                               created_date DATETIME COMMENT '생성일시',
                               modified_date DATETIME COMMENT '수정일시',
                               PRIMARY KEY (course_notice_no)
) COMMENT = '강의별 공지사항';

-- 7. enrollment 테이블
DROP TABLE IF EXISTS enrollment;
CREATE TABLE enrollment (
                            enrollment_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '수강등록번호',
                            enrollment_process DECIMAL(5,2) NOT NULL DEFAULT 0.00 COMMENT '수강진행률 (%)',
                            user_no BIGINT COMMENT '유저번호',
                            course_id BIGINT NOT NULL COMMENT '강의번호',
                            PRIMARY KEY (enrollment_no)
) COMMENT = '수강 등록 내역';

-- 8. evaluations 테이블
DROP TABLE IF EXISTS evaluations;
CREATE TABLE evaluations (
                             evaluation_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '강의평번호',
                             evaluation_score BIGINT NOT NULL COMMENT '강의평 점수',
                             evaluation_desc TEXT NOT NULL COMMENT '강의평내용',
                             course_id BIGINT NOT NULL COMMENT '강의번호',
                             user_no BIGINT COMMENT '유저번호',
                             PRIMARY KEY (evaluation_no)
) COMMENT = '강의 평가';

-- 9. notice 테이블
DROP TABLE IF EXISTS notice;
CREATE TABLE notice (
                        notice_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '공지사항번호',
                        notice_title VARCHAR(255) NOT NULL COMMENT '공지사항 제목',
                        notice_desc TEXT NOT NULL COMMENT '공지사항 내용',
                        created_date DATETIME COMMENT '생성일시',
                        modified_date DATETIME COMMENT '수정일시',
                        PRIMARY KEY (notice_no)
) COMMENT = '전체 공지사항';

-- 10. question_course 테이블
DROP TABLE IF EXISTS question_course;
CREATE TABLE question_course (
                                 question_course_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '강사질문함 번호',
                                 question_course_title VARCHAR(255) NOT NULL COMMENT '강사질문함 제목',
                                 question_course_desc TEXT NOT NULL COMMENT '강사질문함 내용',
                                 user_no BIGINT NOT NULL COMMENT '유저번호',
                                 course_id BIGINT COMMENT '강의번호',
                                 question_course_answer TEXT COMMENT '강사 답변 내용',
                                 created_date DATETIME COMMENT '생성일시',
                                 modified_date DATETIME COMMENT '수정일시',
                                 PRIMARY KEY (question_course_no)
) COMMENT = '강사 질문 게시판';

-- 11. question_tech 테이블
DROP TABLE IF EXISTS question_tech;
CREATE TABLE question_tech (
                               question_tech_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '문의함번호',
                               question_title VARCHAR(255) NOT NULL COMMENT '문의함 제목',
                               question_desc TEXT NOT NULL COMMENT '문의함 내용',
                               user_no BIGINT COMMENT '유저번호',
                               question_status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '문의 처리 상태',
                               question_answer TEXT COMMENT '문의 답변 내용',
                               created_date DATETIME COMMENT '생성일시',
                               question_answered_at DATETIME COMMENT '답변일시',
                               PRIMARY KEY (question_tech_no),
                               INDEX idx_question_tech_status (question_status)
) COMMENT = '기술/일반 문의함';

-- 12. question_tech_count 테이블
DROP TABLE IF EXISTS question_tech_count;
CREATE TABLE question_tech_count (
                                     question_tech_count_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '문의정보번호',
                                     question_title INT NOT NULL DEFAULT 0 COMMENT '문의함 횟수',
                                     user_no BIGINT COMMENT '유저번호',
                                     PRIMARY KEY (question_tech_count_no)
) COMMENT = '유저별 문의 횟수 관리';

-- 13. quiz 테이블
DROP TABLE IF EXISTS quiz;
CREATE TABLE quiz (
                      quiz_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '퀴즈번호',
                      quiz_title VARCHAR(255) NOT NULL COMMENT '퀴즈 제목',
                      chap_no BIGINT COMMENT '챕터번호',
                      PRIMARY KEY (quiz_no)
) COMMENT = '챕터별 퀴즈';

-- 14. quiz_attempt 테이블
DROP TABLE IF EXISTS quiz_attempt;
CREATE TABLE quiz_attempt (
                              quiz_attempt_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '퀴즈시험완료목록',
                              quiz_score INT NOT NULL COMMENT '성적 (맞은 갯수/점수)',
                              quiz_result VARCHAR(20) NOT NULL COMMENT '퀴즈 결과 (CLEAR: 성공, GAME_OVER: 실패)',
                              quiz_no BIGINT COMMENT '퀴즈번호',
                              user_no BIGINT COMMENT '유저번호',
                              PRIMARY KEY (quiz_attempt_id)
) COMMENT = '퀴즈 응시 기록';

-- 15. quiz_list 테이블 (수정됨: quiz_score 추가)
DROP TABLE IF EXISTS quiz_list;
CREATE TABLE quiz_list (
                           quiz_list_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '문제번호',
                           quiz_title VARCHAR(255) NOT NULL COMMENT '문제 제목(내용)',
                           quiz_desc TEXT NOT NULL COMMENT '문제 보기 및 상세설명',
                           quiz_answer VARCHAR(255) NOT NULL COMMENT '문제답',
                           quiz_answer_desc TEXT NOT NULL COMMENT '문제해설',
                           quiz_score BIGINT NOT NULL COMMENT '문제 배점',
                           quiz_no BIGINT COMMENT '소속 퀴즈번호',
                           PRIMARY KEY (quiz_list_no)
) COMMENT = '퀴즈 문항 목록';

-- 16. report 테이블
DROP TABLE IF EXISTS report;
CREATE TABLE report (
                        report_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '신고번호',
                        report_title VARCHAR(255) NOT NULL COMMENT '신고제목',
                        report_desc TEXT NOT NULL COMMENT '신고내용',
                        user_no BIGINT COMMENT '유저번호 (신고자)',
                        report_status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '신고 처리 상태',
                        report_answer TEXT COMMENT '신고 답변 내용',
                        created_date DATETIME COMMENT '생성일시',
                        report_processed_at DATETIME COMMENT '처리일시',
                        PRIMARY KEY (report_no),
                        INDEX idx_report_status (report_status)
) COMMENT = '신고 내역';

-- 17. report_count 테이블
DROP TABLE IF EXISTS report_count;
CREATE TABLE report_count (
                              report_count_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '신고정보관리번호',
                              report_count INT NOT NULL DEFAULT 0 COMMENT '신고당한 횟수',
                              user_no BIGINT NOT NULL COMMENT '유저번호 (대상자)',
                              PRIMARY KEY (report_count_no)
) COMMENT = '유저별 신고당한 횟수 관리';

-- 18. user_account_state

DROP TABLE IF EXISTS user_account_state;
CREATE TABLE user_account_state (
                                    user_no BIGINT NOT NULL PRIMARY KEY,
                                    login_fail_count INT NOT NULL DEFAULT 0 COMMENT '로그인 실패 횟수',
                                    is_account_locked BOOLEAN NOT NULL DEFAULT FALSE COMMENT '계정 잠금 여부',
                                    CONSTRAINT fk_user_account_state_user
                                        FOREIGN KEY (user_no) REFERENCES user(user_no)
) COMMENT = '사용자 계정 상태 정보';

-- ==========================================
-- [2] 더미 데이터 스크립트
-- ==========================================

-- 1. admin
INSERT INTO admin (admin_no, admin_id, admin_password) VALUES
    (1, 'superadmin', '$2a$10$428o3mDo72EhXcd/ItQL5O/pP7Idm0dAr9pjDtan1LaWg/3zU2a5y');
-- 2. user
INSERT INTO user (user_no, user_id, user_password, user_phone_number, user_email, user_role, user_nickname, user_name, user_status, user_gender ) VALUES
                                                                                                                                                      (1, 'teacher01', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-1111-0001', 't1@test.com', 'TEACHER', '김강사', '김철수', 'ACTIVE', 'M'),
                                                                                                                                                      (2, 'teacher02', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-1111-0002', 't2@test.com', 'TEACHER', '이강사', '이영희', 'ACTIVE', 'F'),
                                                                                                                                                      (3, 'teacher03', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-1111-0003', 't3@test.com', 'TEACHER', '박강사', '박민수', 'ACTIVE', 'M'),
                                                                                                                                                      (4, 'teacher04', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-1111-0004', 't4@test.com', 'TEACHER', '최강사', '최수진', 'ACTIVE', 'F'),
                                                                                                                                                      (5, 'teacher05', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-1111-0005', 't5@test.com', 'TEACHER', '정강사', '정동석', 'INACTIVE', 'M'),
                                                                                                                                                      (6, 'stu01', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-2222-0001', 'stu1@test.com', 'STUDENT', '공부왕', '강학생', 'ACTIVE', 'M'),
                                                                                                                                                      (7, 'stu02', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-2222-0002', 'stu2@test.com', 'STUDENT', '합격기원', '조학생', 'ACTIVE', 'F'),
                                                                                                                                                      (8, 'stu03', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-2222-0003', 'stu3@test.com', 'STUDENT', '열공중', '윤학생', 'ACTIVE', 'M'),
                                                                                                                                                      (9, 'stu04', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-2222-0004', 'stu4@test.com', 'STUDENT', '자격증', '장학생', 'ACTIVE', 'F'),
                                                                                                                                                      (10, 'stu05', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-2222-0005', 'stu5@test.com', 'STUDENT', '코딩러', '임학생', 'ACTIVE', 'M'),
                                                                                                                                                      (11, 'stu06', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-2222-0006', 'stu6@test.com', 'STUDENT', '웹마스터', '한학생', 'ACTIVE', 'F'),
                                                                                                                                                      (12, 'stu07', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-2222-0007', 'stu7@test.com', 'STUDENT', '디자이너', '오학생', 'ACTIVE', 'M'),
                                                                                                                                                      (13, 'stu08', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-2222-0008', 'stu8@test.com', 'STUDENT', '데이터맨', '서학생', 'ACTIVE', 'M'),
                                                                                                                                                      (14, 'stu09', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-2222-0009', 'stu9@test.com', 'STUDENT', '프론트', '신학생', 'INACTIVE', 'F'),
                                                                                                                                                      (15, 'stu10', '$2a$10$vxexK2rb1bbLpz9IO1B3j.j4Ag4qCoDoH90JK3sQh6Q/Rl2ZaEBxy', '010-2222-0010', 'stu10@test.com', 'STUDENT', '백엔드', '권학생', 'INACTIVE', 'M');

-- 3. course 더미 데이터 (초등 연산 테마 반영)
INSERT INTO course (course_id, course_title, course_description, course_created_at, course_status, course_send_approve, user_no, created_date, modified_date) VALUES
                                                                                                                                                                  (1, '새싹 덧셈 교실', '초보자를 위한 기초 덧셈', '2023-01-01 10:00:00', 'OPEN', 1, 1, '2023-01-01 09:00:00', '2023-01-01 10:00:00'),
                                                                                                                                                                  (2, '구구단 마스터', '구구단 완벽하게 외우기', '2023-01-02 10:00:00', 'OPEN', 1, 1, '2023-01-02 09:00:00', '2023-01-02 12:30:00'),
                                                                                                                                                                  (3, '나눗셈 첫걸음', '나눗셈의 기초 개념 원리', '2023-01-03 10:00:00', 'OPEN', 1, 2, '2023-01-03 09:00:00', '2023-01-03 10:00:00'),
                                                                                                                                                                  (4, '두 자리 수 덧셈', '받아올림이 있는 덧셈', '2023-01-04 10:00:00', 'CLOSED', 0, 2, '2023-01-04 09:00:00', '2023-01-05 14:00:00'),
                                                                                                                                                                  (5, '두 자리 수 뺄셈', '받아내림이 있는 뺄셈', '2023-01-05 10:00:00', 'OPEN', 1, 3, '2023-01-05 09:00:00', '2023-01-05 10:00:00'),
                                                                                                                                                                  (6, '덧셈과 뺄셈 응용', '덧셈과 뺄셈 섞어 풀기', '2023-01-06 10:00:00', 'OPEN', 1, 3, '2023-01-06 09:00:00', '2023-01-06 10:00:00'),
                                                                                                                                                                  (7, '세 자리 수 덧셈', '백 단위 큰 수 더하기', '2023-01-07 10:00:00', 'OPEN', 1, 4, '2023-01-07 09:00:00', '2023-01-08 09:15:00'),
                                                                                                                                                                  (8, '세 자리 수 뺄셈', '백 단위 큰 수 빼기', '2023-01-08 10:00:00', 'CLOSED', 1, 4, '2023-01-08 09:00:00', '2023-01-08 10:00:00'),
                                                                                                                                                                  (9, '곱셈의 달인', '두 자리 수 이상의 곱셈', '2023-01-09 10:00:00', 'OPEN', 1, 5, '2023-01-09 09:00:00', '2023-01-09 10:00:00'),
                                                                                                                                                                  (10, '나눗셈의 달인', '나머지가 있는 나눗셈', '2023-01-10 10:00:00', 'OPEN', 1, 5, '2023-01-10 09:00:00', '2023-01-11 16:45:00'),
                                                                                                                                                                  (11, '혼합 계산 기초', '사칙연산 순서 알아보기', '2023-01-11 10:00:00', 'OPEN', 1, 1, '2023-01-11 09:00:00', '2023-01-11 10:00:00'),
                                                                                                                                                                  (12, '괄호와 혼합 계산', '괄호가 있는 식 계산하기', '2023-01-12 10:00:00', 'OPEN', 1, 2, '2023-01-12 09:00:00', '2023-01-12 10:00:00'),
                                                                                                                                                                  (13, '스토리텔링 수학 1', '덧셈 뺄셈 문장제 문제', '2023-01-13 10:00:00', 'CLOSED', 0, 3, '2023-01-13 09:00:00', '2023-01-14 11:20:00'),
                                                                                                                                                                  (14, '도형과 분수 기초', '모양과 분수 알아보기', '2023-01-14 10:00:00', 'OPEN', 1, 4, '2023-01-14 09:00:00', '2023-01-14 10:00:00'),
                                                                                                                                                                  (15, '스토리텔링 수학 2', '곱셈 나눗셈 문장제 문제', '2023-01-15 10:00:00', 'OPEN', 1, 5, '2023-01-15 09:00:00', '2023-01-15 10:00:00');

-- 4. chapter 더미 데이터 (초등 연산 테마 반영)
INSERT INTO chapter (chap_no, chap_title, chap_desc, chap_url, course_id) VALUES
                                                                              (1, '덧셈 기초', '한 자리 수의 덧셈 배우기', '/video/math_01.mp4', 1),
                                                                              (2, '뺄셈 기초', '한 자리 수의 뺄셈 배우기', '/video/math_02.mp4', 1),
                                                                              (3, '구구단 원리', '구구단의 규칙 이해하기', '/video/math_03.mp4', 2),
                                                                              (4, '나눗셈 기초', '똑같이 나누어 주는 방법', '/video/math_04.mp4', 3),
                                                                              (5, '두 자리 수 덧셈', '받아올림이 있는 덧셈', '/video/math_05.mp4', 4),
                                                                              (6, '두 자리 수 뺄셈', '받아내림이 있는 뺄셈', '/video/math_06.mp4', 5),
                                                                              (7, '덧셈과 뺄셈 혼합', '순서대로 차근차근 계산하기', '/video/math_07.mp4', 6),
                                                                              (8, '세 자리 수 덧셈', '백 단위의 큰 수 더하기', '/video/math_08.mp4', 7),
                                                                              (9, '세 자리 수 뺄셈', '백 단위의 큰 수 빼기', '/video/math_09.mp4', 8),
                                                                              (10, '곱셈 응용', '두 자리 수 곱하는 방법', '/video/math_10.mp4', 9),
                                                                              (11, '나눗셈 응용', '몫과 나머지 구하기', '/video/math_11.mp4', 10),
                                                                              (12, '혼합 계산 1', '곱셈과 나눗셈 먼저 계산하기', '/video/math_12.mp4', 11),
                                                                              (13, '혼합 계산 2', '괄호 안을 가장 먼저 계산하기', '/video/math_13.mp4', 12),
                                                                              (14, '생활 속 문장제 1', '덧셈과 뺄셈 이야기 문제 풀이', '/video/math_14.mp4', 13),
                                                                              (15, '생활 속 문장제 2', '곱셈과 나눗셈 이야기 문제 풀이', '/video/math_15.mp4', 15);

-- 5. chapter_attempt
INSERT INTO chapter_attempt (chapter_attempt_id, user_no, chap_no) VALUES
                                                                       (1, 6, 1), (2, 6, 2), (3, 7, 3), (4, 8, 4), (5, 9, 5),
                                                                       (6, 10, 6), (7, 11, 7), (8, 12, 8), (9, 13, 9), (10, 14, 10),
                                                                       (11, 15, 11), (12, 6, 12), (13, 7, 13), (14, 8, 14), (15, 9, 15);

-- 6. course_notice
INSERT INTO course_notice (course_notice_no, course_notice_title, course_notice_desc, user_no, course_id, created_date, modified_date) VALUES
                                                                                                                                           (1, '개강 안내', '강의가 시작되었습니다.', 1, 1, '2024-04-01 09:00:00', NULL),
                                                                                                                                           (2, '교재 안내', '교재 파일 첨부합니다.', 1, 2, '2024-04-02 10:00:00', '2024-04-02 11:00:00'),
                                                                                                                                           (3, '파이썬 실습', '실습 환경 설정 필독', 2, 3, '2024-04-03 11:30:00', NULL),
                                                                                                                                           (4, '휴강 안내', '이번 주 휴강입니다.', 2, 4, '2024-04-04 14:00:00', '2024-04-04 15:00:00'),
                                                                                                                                           (5, '과제 제출', '과제 기한 엄수 바랍니다.', 3, 5, '2024-04-05 09:15:00', NULL),
                                                                                                                                           (6, '시험 일정', '중간고사 일정입니다.', 3, 6, '2024-04-06 10:20:00', '2024-04-06 12:00:00'),
                                                                                                                                           (7, '리액트 Q&A', '질문은 게시판 이용', 4, 7, '2024-04-07 13:00:00', NULL),
                                                                                                                                           (8, 'Vue 참고자료', '공식 문서 링크', 4, 8, '2024-04-08 16:45:00', '2024-04-09 09:00:00'),
                                                                                                                                           (9, '오라클 설치', '설치 가이드', 5, 9, '2024-04-09 11:00:00', NULL),
                                                                                                                                           (10, '튜닝 주의사항', 'DB 백업 필수', 5, 10, '2024-04-10 09:30:00', '2024-04-10 10:30:00'),
                                                                                                                                           (11, '알고리즘 사이트', '백준, 프로그래머스', 1, 11, '2024-04-11 14:20:00', NULL),
                                                                                                                                           (12, '자료구조 과제', '과제 안내', 2, 12, '2024-04-12 10:15:00', '2024-04-12 11:00:00'),
                                                                                                                                           (13, '네트워크 실습', '패킷 트레이서 설치', 3, 13, '2024-04-13 13:50:00', NULL),
                                                                                                                                           (14, '운영체제 교재', '공룡책 참고', 4, 14, '2024-04-14 16:10:00', '2024-04-14 17:00:00'),
                                                                                                                                           (15, '도커 허브', '계정 생성 안내', 5, 15, '2024-04-15 09:40:00', NULL);

-- 7. enrollment
INSERT INTO enrollment (enrollment_no, enrollment_process, user_no, course_id) VALUES
                                                                                   (1, 100.00, 6, 1), (2, 50.50, 7, 2), (3, 20.00, 8, 3), (4, 0.00, 9, 4), (5, 80.00, 10, 5),
                                                                                   (6, 10.00, 11, 6), (7, 45.00, 12, 7), (8, 90.00, 13, 8), (9, 30.00, 14, 9), (10, 100.00, 15, 10),
                                                                                   (11, 75.00, 6, 11), (12, 60.00, 7, 12), (13, 0.00, 8, 13), (14, 15.00, 9, 14), (15, 95.00, 10, 15);

-- 8. evaluations
INSERT INTO evaluations (evaluation_no, evaluation_score, evaluation_desc, course_id, user_no) VALUES
                                                                                                   (1, 5, '최고의 강의입니다!', 1, 6), (2, 4, '유익했습니다.', 2, 7),
                                                                                                   (3, 4, '설명이 좋아요.', 3, 8), (4, 3, '조금 어렵네요.', 5, 10),
                                                                                                   (5, 5, '실무에 도움됩니다.', 6, 11), (6, 4, '리액트 완벽 마스터', 7, 12),
                                                                                                   (7, 4, '뷰 기초 다지기 좋음', 8, 13), (8, 3, 'DB는 역시 어려워요', 9, 14),
                                                                                                   (9, 5, '명강의입니다.', 10, 15), (10, 4, '알고리즘 이해 쏙쏙', 11, 6),
                                                                                                   (11, 4, '기본기 탄탄', 12, 7), (12, 2, '소리가 잘 안들려요', 1, 8),
                                                                                                   (13, 5, '운영체제 정복!', 14, 9), (14, 4, '도커 신세계', 15, 10),
                                                                                                   (15, 5, '강추합니다.', 2, 11);

-- 9. notice
INSERT INTO notice (notice_no, notice_title, notice_desc, created_date, modified_date) VALUES
                                                                                           (1, '사이트 오픈 안내', '환영합니다!', '2024-01-01 09:00:00', '2024-01-01 09:00:00'), (2, '점검 안내', '새벽 2시 점검', '2024-01-15 14:00:00', '2024-01-15 15:00:00'), (3, '이벤트', '수강평 작성 이벤트', '2024-02-01 10:00:00', NULL), (4, '서버 안정화', '안정화 완료', '2024-02-10 11:20:00', '2024-02-10 11:30:00'), (5, '신규 강의', '다음달 신규 강의', '2024-03-01 09:00:00', NULL), (6, '앱 출시', '모바일 앱 출시', '2024-03-15 13:45:00', '2024-03-15 13:45:00'), (7, '이용약관 변경', '약관 변경 안내', '2024-04-01 10:00:00', '2024-04-02 09:30:00'), (8, '개인정보 처리방침', '처리방침 개정', '2024-04-10 15:00:00', NULL), (9, '결제 오류 안내', '복구 완료', '2024-04-20 16:30:00', '2024-04-20 17:00:00'), (10, '추석 연휴', '고객센터 휴무', '2024-09-01 09:00:00', NULL), (11, '설날 연휴', '고객센터 휴무', '2024-12-20 10:00:00', NULL), (12, '수강권 할인', '기간 한정 세일', '2024-11-11 11:11:00', '2024-11-11 12:00:00'), (13, '우수 수강생', '이달의 수강생 발표', '2024-05-01 09:00:00', NULL), (14, '강사 모집', '신규 강사 채용', '2024-06-01 10:00:00', '2024-06-01 11:00:00'), (15, '환불 규정', '환불 규정 안내', '2024-07-01 09:00:00', '2024-07-05 14:20:00');

-- 10. question_course (전체 답변 완료)
INSERT INTO question_course (question_course_no, question_course_title, question_course_desc, user_no, course_id, question_course_answer, created_date, modified_date) VALUES
                                                                                                                                                                           (1, '이해가 안가요', '이 부분 설명 좀..', 6, 1, '해당 부분은 2강을 다시 참고하시면 도움이 됩니다.', '2024-04-01 10:00:00', '2024-04-01 11:00:00'),
                                                                                                                                                                           (2, '오류가 납니다', '에러코드 첨부', 7, 2, '설정 파일의 오타를 확인해보세요.', '2024-04-02 11:30:00', '2024-04-02 14:20:00'),
                                                                                                                                                                           (3, '파이썬 버전', '몇 버전 써야하나요?', 8, 3, '3.9 이상 버전을 권장합니다.', '2024-04-03 09:15:00', '2024-04-03 10:45:00'),
                                                                                                                                                                           (4, '수학 지식', '선형대수 필수인가요?', 9, 4, '기본적인 행렬 연산 지식이 있으면 좋습니다.', '2024-04-04 15:00:00', '2024-04-04 16:30:00'),
                                                                                                                                                                           (5, 'CSS가 안먹혀요', '선택자 질문', 10, 5, '클래스명 앞에 마침표(.)를 빼먹으신 것 같네요.', '2024-04-05 13:20:00', '2024-04-05 15:10:00'),
                                                                                                                                                                           (6, '비동기 처리', 'Promise와 async 차이', 11, 6, 'Promise는 객체, async/await는 이를 더 직관적으로 쓰는 문법입니다.', '2024-04-06 10:40:00', '2024-04-06 11:50:00'),
                                                                                                                                                                           (7, '리액트 라우터', 'v6 업데이트 질문', 12, 7, 'v6에서는 Routes와 Route 컴포넌트를 주로 사용합니다.', '2024-04-07 14:10:00', '2024-04-07 16:00:00'),
                                                                                                                                                                           (8, '컴포넌트 통신', 'props 질문', 13, 8, '부모에서 자식으로는 props, 자식에서 부모로는 콜백 함수를 사용합니다.', '2024-04-08 16:45:00', '2024-04-09 09:20:00'),
                                                                                                                                                                           (9, '조인 질문', 'LEFT JOIN 헷갈려요', 14, 9, 'LEFT JOIN은 왼쪽 테이블의 모든 데이터를 가져오고 우측은 매칭되는 것만 가져옵니다.', '2024-04-09 11:00:00', '2024-04-09 13:15:00'),
                                                                                                                                                                           (10, '인덱스 타는지', '실행계획 확인법', 15, 10, 'EXPLAIN 명령어를 쿼리 앞에 붙여서 type 컬럼을 확인하세요.', '2024-04-10 09:30:00', '2024-04-10 11:40:00'),
                                                                                                                                                                           (11, '시간복잡도', '이 코드 O(n) 맞나요?', 6, 11, '네 맞습니다. 단일 for문이 사용되었네요.', '2024-04-11 14:20:00', '2024-04-11 15:30:00'),
                                                                                                                                                                           (12, '트리 탐색', 'DFS BFS 비교', 7, 12, 'DFS는 깊이, BFS는 너비를 우선 탐색합니다.', '2024-04-12 10:15:00', '2024-04-12 11:25:00'),
                                                                                                                                                                           (13, '포트포워딩', '이해를 못했습니다', 8, 13, '공유기 설정에서 특정 포트로 들어오는 외부 접속을 내부 PC로 연결해주는 기능입니다.', '2024-04-13 13:50:00', '2024-04-13 15:10:00'),
                                                                                                                                                                           (14, '데드락', '교착상태 예제', 9, 14, '두 스레드가 서로의 락이 풀리기를 영원히 기다리는 상태를 말합니다.', '2024-04-14 16:10:00', '2024-04-14 17:30:00'),
                                                                                                                                                                           (15, 'yaml 작성법', '띄어쓰기 오류', 10, 15, 'yaml은 들여쓰기 2칸을 엄격하게 지켜야 합니다. 탭 대신 스페이스를 쓰세요.', '2024-04-15 09:40:00', '2024-04-15 10:50:00');
-- 11. question_tech
INSERT INTO question_tech (question_tech_no, question_title, question_desc, user_no, question_status, question_answer, created_date, question_answered_at) VALUES
                                                                                                                                                               (1, '동영상 재생 오류', '화면이 까맣게 나와요', 6, 'RESOLVED', '캐시 삭제 후 재접속 해보시길 바랍니다.', '2024-04-01 10:15:00', '2024-04-01 11:30:00'),
                                                                                                                                                               (2, '결제 취소', '환불해주세요', 7, 'RESOLVED', '환불 규정에 따라 처리 완료되었습니다.', '2024-04-02 09:20:00', '2024-04-02 14:00:00'),
                                                                                                                                                               (3, '수강기간 연장', '연장 가능한가요?', 8, 'RESOLVED', '1주일 무료 연장 처리 도와드렸습니다.', '2024-04-03 13:45:00', '2024-04-03 15:10:00'),
                                                                                                                                                               (4, '아이디 찾기', '이메일 변경요청', 9, 'RESOLVED', '고객센터로 본인인증 서류를 보내주시면 변경 가능합니다.', '2024-04-04 11:00:00', '2024-04-04 13:25:00'),
                                                                                                                                                               (5, '영수증 발급', '어디서 하나요?', 10, 'RESOLVED', '마이페이지 > 결제내역에서 출력 가능합니다.', '2024-04-05 16:30:00', '2024-04-06 09:00:00'),
                                                                                                                                                               (6, '모바일 재생', '앱에서 끊겨요', 11, 'RESOLVED', '앱 최신 버전으로 업데이트 부탁드립니다.', '2024-04-06 10:10:00', '2024-04-06 11:15:00'),
                                                                                                                                                               (7, '교재 다운로드', '파일이 안열림', 12, 'RESOLVED', 'PDF 리더 프로그램이 설치되어 있는지 확인해주세요.', '2024-04-07 14:50:00', '2024-04-07 15:30:00'),
                                                                                                                                                               (8, '회원 탈퇴', '탈퇴 메뉴 못찾겠어요', 13, 'RESOLVED', '마이페이지 하단 설정 메뉴에서 진행하실 수 있습니다.', '2024-04-08 09:05:00', '2024-04-08 10:20:00'),
                                                                                                                                                               (9, '비밀번호 변경', '링크가 만료됨', 14, 'RESOLVED', '가입하신 이메일로 재설정 링크를 다시 발송해 드렸습니다.', '2024-04-09 11:40:00', '2024-04-09 13:00:00'),
                                                                                                                                                               (10, '강사 지원', '자격 요건 문의', 15, 'RESOLVED', '공지사항의 강사 채용 공고를 확인해 주시기 바랍니다.', '2024-04-10 15:20:00', '2024-04-10 17:10:00'),
                                                                                                                                                               (11, '수료증 발급', '이름 수정해주세요', 6, 'RESOLVED', '요청하신 영문 이름으로 수정하여 재발급 처리했습니다.', '2024-04-11 10:30:00', '2024-04-11 11:45:00'),
                                                                                                                                                               (12, '수강 확인서', '도장 찍힌거 필요', 7, 'RESOLVED', '고객센터 1:1 문의로 이메일 남겨주시면 PDF본을 발송해드립니다.', '2024-04-12 13:15:00', '2024-04-12 14:50:00'),
                                                                                                                                                               (13, '화면 해상도', '1080p 안되나요', 8, 'RESOLVED', '우측 하단 톱니바퀴 아이콘에서 1080p 해상도로 변경 가능합니다.', '2024-04-13 16:00:00', '2024-04-14 09:20:00'),
                                                                                                                                                               (14, '자막 지원', '영어 자막 있나요', 9, 'RESOLVED', '현재 자바 기초 강의에 한하여 영문 자막을 제공하고 있습니다.', '2024-04-14 11:25:00', '2024-04-14 13:40:00'),
                                                                                                                                                               (15, '포인트 사용', '결제시 포인트 미적용', 10, 'RESOLVED', '결제 시 1000포인트 이상부터 10원 단위로 사용 가능합니다.', '2024-04-15 14:10:00', '2024-04-15 15:30:00');

-- 12. question_tech_count
INSERT INTO question_tech_count (question_tech_count_no, question_title, user_no) VALUES
                                                                                      (1, 1, 6), (2, 1, 7), (3, 1, 8), (4, 1, 9), (5, 1, 10),
                                                                                      (6, 1, 11), (7, 1, 12), (8, 1, 13), (9, 1, 14), (10, 1, 15),
                                                                                      (11, 2, 6), (12, 2, 7), (13, 2, 8), (14, 2, 9), (15, 2, 10);

-- 13. quiz 더미 데이터 (초등 연산 테마 반영)
INSERT INTO quiz (quiz_no, quiz_title, chap_no) VALUES
                                                    (1, '덧셈 기초', 1),
                                                    (2, '뺄셈 기초', 2),
                                                    (3, '구구단 퀴즈', 3),
                                                    (4, '나눗셈 기초', 4),
                                                    (5, '두 자리 수 덧셈', 5),
                                                    (6, '두 자리 수 뺄셈', 6),
                                                    (7, '덧셈과 뺄셈 혼합', 7),
                                                    (8, '세 자리 수 덧셈', 8),
                                                    (9, '세 자리 수 뺄셈', 9),
                                                    (10, '곱셈 응용', 10),
                                                    (11, '나눗셈 응용', 11),
                                                    (12, '혼합 계산 1 (괄호 없는 식)', 12),
                                                    (13, '혼합 계산 2 (괄호 있는 식)', 13),
                                                    (14, '생활 속 문장제 1 (덧셈, 뺄셈)', 14),
                                                    (15, '생활 속 문장제 2 (곱셈, 나눗셈)', 15);

-- 14. quiz_attempt 더미 데이터
INSERT INTO quiz_attempt (quiz_attempt_id, quiz_score, quiz_result, quiz_no, user_no) VALUES
                                                                                          (1, 100, 'CLEAR', 1, 6),
                                                                                          (2, 50, 'CLEAR', 2, 6),
                                                                                          (3, 100, 'CLEAR', 3, 7),
                                                                                          (4, 50, 'CLEAR', 4, 8),
                                                                                          (5, 50, 'CLEAR', 5, 9),
                                                                                          (6, 70, 'CLEAR', 6, 10),
                                                                                          (7, 100, 'CLEAR', 7, 11),
                                                                                          (8, 50, 'CLEAR', 8, 12),
                                                                                          (9, 50, 'CLEAR', 9, 13),
                                                                                          (10, 100, 'CLEAR', 10, 14),
                                                                                          (11, 50, 'CLEAR', 11, 15),
                                                                                          (12, 0, 'GAME_OVER', 12, 6),
                                                                                          (13, 100, 'CLEAR', 13, 7),
                                                                                          (14, 50, 'CLEAR', 14, 8),
                                                                                          (15, 100, 'CLEAR', 15, 9);

-- 15. quiz_list 더미 데이터 (초등 연산, 한 퀴즈당 5문항, 문항당 20점, 합산 100점)
INSERT INTO quiz_list (quiz_list_no, quiz_title, quiz_desc, quiz_answer, quiz_answer_desc, quiz_score, quiz_no) VALUES

-- [Quiz 1] 덧셈 기초 (총 100점)
(1, '1 + 2는 얼마일까요?', '1 다음다음 숫자를 생각해보세요.', '3', '1에 2를 더하면 3이 됩니다.', 20, 1),
(2, '4 + 5는 얼마일까요?', '손가락 4개와 5개를 합쳐보세요.', '9', '4와 5를 더하면 9입니다.', 20, 1),
(3, '7 + 3은 얼마일까요?', '7에서 3을 더하면 딱 떨어지는 수가 됩니다.', '10', '7과 3이 만나면 10이 됩니다.', 20, 1),
(4, '6 + 8은 얼마일까요?', '8에 2를 먼저 주어 10을 만들고 나머지를 더해보세요.', '14', '6과 8을 더하면 10이 넘어가서 14가 됩니다.', 20, 1),
(5, '9 + 9는 얼마일까요?', '10 더하기 10에서 2를 빼보세요.', '18', '9가 두 개 있으면 18이 됩니다.', 20, 1),

-- [Quiz 2] 뺄셈 기초 (총 100점)
(6, '5 - 2는 얼마일까요?', '다섯 개 중 두 개를 지워보세요.', '3', '5에서 2를 빼면 3이 남습니다.', 20, 2),
(7, '8 - 4는 얼마일까요?', '8의 절반을 생각해보세요.', '4', '8의 절반인 4를 빼면 4가 남습니다.', 20, 2),
(8, '10 - 3은 얼마일까요?', '열 손가락 중 세 개를 접어보세요.', '7', '10개 중에서 3개를 지우면 7개가 남습니다.', 20, 2),
(9, '15 - 6은 얼마일까요?', '15에서 5를 먼저 빼고 1을 더 빼보세요.', '9', '15에서 6을 빼면 9가 됩니다.', 20, 2),
(10, '20 - 8은 얼마일까요?', '10에서 8을 뺀 수에 10을 더해보세요.', '12', '20에서 8을 빼면 12가 됩니다.', 20, 2),

-- [Quiz 3] 구구단 퀴즈 (총 100점)
(11, '2 x 3은 얼마일까요?', '2를 세 번 더해보세요.', '6', '2를 3번 더한 값과 같은 6입니다.', 20, 3),
(12, '4 x 5는 얼마일까요?', '4를 다섯 번 더해보세요.', '20', '4를 5번 곱하면 20이 됩니다.', 20, 3),
(13, '6 x 7은 얼마일까요?', '구구단 6단을 외워보세요.', '42', '구구단 6단, 육칠에 42입니다.', 20, 3),
(14, '8 x 9는 얼마일까요?', '구구단 8단을 외워보세요.', '72', '구구단 8단, 팔구 72입니다.', 20, 3),
(15, '9 x 9는 얼마일까요?', '구구단 9단의 마지막입니다.', '81', '구구단 9단, 구구 81입니다.', 20, 3),

-- [Quiz 4] 나눗셈 기초 (총 100점)
(16, '6 ÷ 2는 얼마일까요?', '6을 반으로 나누어보세요.', '3', '6을 2로 똑같이 나누면 3이 됩니다.', 20, 4),
(17, '15 ÷ 3은 얼마일까요?', '3에 무엇을 곱해야 15가 될까요?', '5', '3에 5를 곱해야 15가 되므로 정답은 5입니다.', 20, 4),
(18, '24 ÷ 4는 얼마일까요?', '4단에서 24가 나오는 수를 찾아보세요.', '6', '4 x 6 = 24 입니다.', 20, 4),
(19, '35 ÷ 5는 얼마일까요?', '5단에서 35가 나오는 수를 찾아보세요.', '7', '5 x 7 = 35 입니다.', 20, 4),
(20, '49 ÷ 7은 얼마일까요?', '7단에서 49가 나오는 수를 찾아보세요.', '7', '7 x 7 = 49 입니다.', 20, 4),

-- [Quiz 5] 두 자리 수 덧셈 (총 100점)
(21, '10 + 15는 얼마일까요?', '일의 자리는 5가 됩니다.', '25', '십의 자리와 일의 자리를 각각 더합니다.', 20, 5),
(22, '22 + 13은 얼마일까요?', '십의 자리와 일의 자리를 따로 더해보세요.', '35', '20+10=30, 2+3=5 이므로 35입니다.', 20, 5),
(23, '34 + 48은 얼마일까요?', '일의 자리 4와 8을 먼저 더해보세요.', '82', '일의 자리 4+8=12이므로 받아올림을 합니다.', 20, 5),
(24, '50 + 30은 얼마일까요?', '5와 3을 더하고 0을 붙여보세요.', '80', '5 더하기 3을 한 뒤 0을 붙이면 쉽습니다.', 20, 5),
(25, '60 + 40은 얼마일까요?', '6과 4를 더하면 10이 됩니다.', '100', '60과 40이 더해지면 딱 100이 됩니다.', 20, 5),

-- [Quiz 6] 두 자리 수 뺄셈 (총 100점)
(26, '30 - 10은 얼마일까요?', '3에서 1을 빼고 0을 붙여보세요.', '20', '3에서 1을 빼는 것과 같습니다.', 20, 6),
(27, '45 - 20은 얼마일까요?', '십의 자리 숫자만 빼보세요.', '25', '십의 자리만 4에서 2를 빼면 됩니다.', 20, 6),
(28, '50 - 15은 얼마일까요?', '50에서 10을 빼고 다시 5를 빼보세요.', '35', '50에서 10을 먼저 빼고 5를 더 뺍니다.', 20, 6),
(29, '72 - 28은 얼마일까요?', '일의 자리에서 받아내림이 필요해요.', '44', '일의 자리에서 받아내림을 하여 계산합니다.', 20, 6),
(30, '99 - 44는 얼마일까요?', '각 자리 숫자를 똑같이 빼주세요.', '55', '각 자리수끼리 빼주면 55가 됩니다.', 20, 6),

-- [Quiz 7] 덧셈과 뺄셈 혼합 (총 100점)
(31, '10 + 5 - 2는 얼마일까요?', '앞에서부터 차례대로 계산하세요.', '13', '10 더하기 5는 15, 거기서 2를 빼면 13입니다.', 20, 7),
(32, '20 - 5 + 3은 얼마일까요?', '먼저 20에서 5를 빼보세요.', '18', '20에서 5를 빼면 15, 다시 3을 더하면 18입니다.', 20, 7),
(33, '15 + 10 - 5는 얼마일까요?', '15 더하기 10을 먼저 하세요.', '20', '순서대로 차근차근 계산합니다.', 20, 7),
(34, '30 - 10 + 8은 얼마일까요?', '30 빼기 10을 먼저 하세요.', '28', '30에서 10을 먼저 빼면 20, 8을 더해 28입니다.', 20, 7),
(35, '50 + 20 - 10은 얼마일까요?', '50 더하기 20을 먼저 하세요.', '60', '70에서 10을 빼면 60이 됩니다.', 20, 7),

-- [Quiz 8] 세 자리 수 덧셈 (총 100점)
(36, '100 + 200은 얼마일까요?', '1 더하기 2에 0을 두 개 붙여보세요.', '300', '1 더하기 2를 하고 0을 두 개 붙입니다.', 20, 8),
(37, '150 + 150은 얼마일까요?', '15 더하기 15를 계산해보세요.', '300', '15 더하기 15를 한 뒤 0을 붙입니다.', 20, 8),
(38, '220 + 130은 얼마일까요?', '각 자리수를 맞춰서 더해보세요.', '350', '백, 십, 일의 자리를 순서대로 더합니다.', 20, 8),
(39, '450 + 360은 얼마일까요?', '십의 자리에서 받아올림이 필요해요.', '810', '십의 자리에서 받아올림이 발생합니다.', 20, 8),
(40, '550 + 450은 얼마일까요?', '50과 50을 더하면 100이 됩니다.', '1000', '두 수를 더하면 딱 1000이 됩니다.', 20, 8),

-- [Quiz 9] 세 자리 수 뺄셈 (총 100점)
(41, '300 - 100은 얼마일까요?', '3에서 1을 뺀 수에 0을 두 개 붙이세요.', '200', '3에서 1을 뺀 뒤 0을 두 개 붙입니다.', 20, 9),
(42, '500 - 250은 얼마일까요?', '500의 절반을 빼는 것과 같아요.', '250', '500의 딱 절반을 뺐습니다.', 20, 9),
(43, '450 - 120은 얼마일까요?', '각 자리수를 맞춰서 빼보세요.', '330', '각 자리수를 맞추어 빼줍니다.', 20, 9),
(44, '600 - 150은 얼마일까요?', '백의 자리에서 받아내림이 필요해요.', '450', '백의 자리에서 받아내림을 해야 합니다.', 20, 9),
(45, '800 - 350은 얼마일까요?', '800에서 먼저 300을 빼보세요.', '450', '800에서 300을 빼고 50을 마저 뺍니다.', 20, 9),

-- [Quiz 10] 곱셈 응용 (총 100점)
(46, '12 x 2는 얼마일까요?', '12를 두 번 더해보세요.', '24', '12를 두 번 더한 것과 같습니다.', 20, 10),
(47, '15 x 3은 얼마일까요?', '15 더하기 15 더하기 15를 해보세요.', '45', '10x3=30, 5x3=15 이므로 더해서 45입니다.', 20, 10),
(48, '20 x 4는 얼마일까요?', '2 곱하기 4 뒤에 0을 붙여보세요.', '80', '2x4=8 뒤에 0을 붙입니다.', 20, 10),
(49, '25 x 4는 얼마일까요?', '25가 4개 모이면 100 단위가 됩니다.', '100', '25가 4개 모이면 100이 됩니다.', 20, 10),
(50, '30 x 5는 얼마일까요?', '3 곱하기 5 뒤에 0을 붙여보세요.', '150', '3x5=15 뒤에 0을 붙여 150이 됩니다.', 20, 10),

-- [Quiz 11] 나눗셈 응용 (총 100점)
(51, '40 ÷ 2는 얼마일까요?', '40의 절반을 구해보세요.', '20', '40의 절반은 20입니다.', 20, 11),
(52, '100 ÷ 2는 얼마일까요?', '100을 두 사람에게 똑같이 나누어주세요.', '50', '100을 두 명에게 똑같이 나누면 50씩입니다.', 20, 11),
(53, '150 ÷ 3은 얼마일까요?', '15 나누기 3 뒤에 0을 붙여보세요.', '50', '15 ÷ 3 = 5 뒤에 0을 붙입니다.', 20, 11),
(54, '300 ÷ 5는 얼마일까요?', '30 나누기 5 뒤에 0을 붙여보세요.', '60', '30 ÷ 5 = 6 뒤에 0을 붙입니다.', 20, 11),
(55, '400 ÷ 8은 얼마일까요?', '40 나누기 8 뒤에 0을 붙여보세요.', '50', '40 ÷ 8 = 5 뒤에 0을 붙입니다.', 20, 11),

-- [Quiz 12] 혼합 계산 1 (괄호 없는 식) (총 100점)
(56, '2 + 3 x 4는 얼마일까요?', '곱셈을 먼저 계산해야 해요.', '14', '덧셈보다 곱셈(3x4=12)을 먼저 계산합니다.', 20, 12),
(57, '10 - 2 x 3은 얼마일까요?', '곱셈을 먼저 하고 빼야 해요.', '4', '곱셈(2x3=6)을 먼저 하고 10에서 뺍니다.', 20, 12),
(58, '15 - 10 ÷ 2는 얼마일까요?', '나눗셈을 먼저 계산하세요.', '10', '나눗셈(10÷2=5)을 먼저 계산합니다.', 20, 12),
(59, '8 + 12 ÷ 4는 얼마일까요?', '나눗셈을 먼저 하고 더하세요.', '11', '나눗셈(12÷4=3)을 먼저 한 후 8과 더합니다.', 20, 12),
(60, '5 x 4 - 10은 얼마일까요?', '곱셈을 먼저 한 후 10을 빼세요.', '10', '곱셈(20)에서 10을 뺍니다.', 20, 12),

-- [Quiz 13] 혼합 계산 2 (괄호 있는 식) (총 100점)
(61, '(5 + 5) x 2는 얼마일까요?', '괄호 안을 먼저 계산하세요.', '20', '괄호 안의 덧셈(10)을 먼저 하고 2를 곱합니다.', 20, 13),
(62, '20 ÷ (2 + 3)은 얼마일까요?', '괄호 안의 덧셈을 먼저 하세요.', '4', '괄호 안(5)을 먼저 계산하고 20을 나눕니다.', 20, 13),
(63, '3 x (4 + 2)는 얼마일까요?', '괄호 안을 먼저 더하고 3을 곱하세요.', '18', '괄호 안(6)을 먼저 계산하고 3을 곱합니다.', 20, 13),
(64, '(10 - 4) x 3은 얼마일까요?', '괄호 안의 뺄셈을 먼저 하세요.', '18', '괄호 안(6)을 먼저 계산하고 3을 곱합니다.', 20, 13),
(65, '30 ÷ (10 - 5)는 얼마일까요?', '괄호 안의 뺄셈을 먼저 하세요.', '6', '괄호 안(5)을 먼저 계산하고 30을 나눕니다.', 20, 13),

-- [Quiz 14] 생활 속 문장제 1 (덧셈, 뺄셈) (총 100점)
(66, '사과 5개와 3개를 합치면 몇 개인가요?', '5 더하기 3을 계산해보세요.', '8', '5 + 3 = 8개입니다.', 20, 14),
(67, '사탕 10개 중 4개를 먹으면 몇 개 남나요?', '10 빼기 4를 계산해보세요.', '6', '10 - 4 = 6개입니다.', 20, 14),
(68, '연필 12자루에 12자루를 더 사면 몇 자루인가요?', '12 더하기 12를 계산해보세요.', '24', '12 + 12 = 24자루입니다.', 20, 14),
(69, '우유 500ml 중 200ml를 마시면 몇 ml 남나요?', '500 빼기 200을 계산해보세요.', '300', '500 - 200 = 300ml입니다.', 20, 14),
(70, '1000원을 내고 500원짜리를 사면 거스름돈은?', '1000 빼기 500을 계산해보세요.', '500', '1000 - 500 = 500원입니다.', 20, 14),

-- [Quiz 15] 생활 속 문장제 2 (곱셈, 나눗셈) (총 100점)
(71, '과자 2개씩 3봉지는 총 몇 개인가요?', '2 곱하기 3을 계산해보세요.', '6', '2 x 3 = 6개입니다.', 20, 15),
(72, '학생 20명을 4명씩 묶으면 몇 모둠인가요?', '20 나누기 4를 계산해보세요.', '5', '20 ÷ 4 = 5모둠입니다.', 20, 15),
(73, '친구 5명에게 구슬을 5개씩 주려면 총 몇 개?', '5 곱하기 5를 계산해보세요.', '25', '5 x 5 = 25개입니다.', 20, 15),
(74, '피자 8조각을 2명이 똑같이 나누면 1명당 몇 조각?', '8 나누기 2를 계산해보세요.', '4', '8 ÷ 2 = 4조각입니다.', 20, 15),
(75, '500원씩 4일 동안 모으면 총 얼마인가요?', '500 곱하기 4를 계산해보세요.', '2000', '500 x 4 = 2000원입니다.', 20, 15);

-- 16. report
INSERT INTO report (report_no, report_title, report_desc, user_no, report_status, report_answer, created_date, report_processed_at) VALUES
                                                                                                                                        (1, '욕설 신고', '게시판에서 욕설 사용', 6, 'RESOLVED', '해당 회원에게 1차 경고 조치 하였습니다.', '2024-04-01 12:00:00', '2024-04-01 14:30:00'),
                                                                                                                                        (2, '도배 신고', '질문게시판 도배', 7, 'RESOLVED', '도배성 게시물은 모두 삭제 처리되었습니다.', '2024-04-02 15:10:00', '2024-04-02 17:20:00'),
                                                                                                                                        (3, '광고 신고', '불법 도박 사이트 홍보', 8, 'RESOLVED', '해당 계정 영구 정지 처리 완료했습니다.', '2024-04-03 09:45:00', '2024-04-03 11:00:00'),
                                                                                                                                        (4, '저작권 위반', '강의 무단 배포 링크', 9, 'RESOLVED', '관련 링크 차단 및 계정 정지 조치했습니다.', '2024-04-04 16:20:00', '2024-04-05 09:30:00'),
                                                                                                                                        (5, '비방 신고', '강사 비방', 10, 'RESOLVED', '블라인드 처리 및 주의 조치 완료.', '2024-04-05 10:15:00', '2024-04-05 11:45:00'),
                                                                                                                                        (6, '스팸 메일', '스팸 쪽지 발송', 11, 'RESOLVED', '해당 발송자의 쪽지 발송 권한을 차단했습니다.', '2024-04-06 14:30:00', '2024-04-06 16:10:00'),
                                                                                                                                        (7, '음란물 신고', '부적절한 프로필 사진', 12, 'RESOLVED', '해당 프로필 사진 삭제 및 계정 경고 조치했습니다.', '2024-04-07 11:50:00', '2024-04-07 13:20:00'),
                                                                                                                                        (8, '명의 도용', '제 아이디를 사칭합니다', 13, 'RESOLVED', '본인 확인 절차를 거쳐 사칭 계정을 정지 처리했습니다.', '2024-04-08 09:30:00', '2024-04-08 14:00:00'),
                                                                                                                                        (9, '욕설 신고 2', '수강평에 욕설', 14, 'RESOLVED', '수강평 삭제 처리 및 작성자에게 경고 메시지를 발송했습니다.', '2024-04-09 15:40:00', '2024-04-09 17:15:00'),
                                                                                                                                        (10, '광고 신고 2', '홍보글', 15, 'RESOLVED', '홍보글 전면 삭제 및 스팸 IP 차단 완료했습니다.', '2024-04-10 11:00:00', '2024-04-10 13:00:00'),
                                                                                                                                        (11, '도배 신고 2', '같은 질문 10번 올림', 6, 'RESOLVED', '중복 질문글 삭제 및 3일간 게시판 글쓰기 정지 조치했습니다.', '2024-04-11 13:20:00', '2024-04-11 16:40:00'),
                                                                                                                                        (12, '부적절 닉네임', '닉네임이 불쾌함', 7, 'RESOLVED', '해당 유저 닉네임을 임의 변경 조치 및 경고했습니다.', '2024-04-12 10:45:00', '2024-04-12 11:50:00'),
                                                                                                                                        (13, '싸움 신고', '회원간 분쟁', 8, 'RESOLVED', '관련된 두 회원 모두에게 커뮤니티 가이드라인 위반 경고 조치했습니다.', '2024-04-13 15:30:00', '2024-04-13 18:00:00'),
                                                                                                                                        (14, '버그 악용', '포인트 버그 사용', 9, 'RESOLVED', '버그로 획득한 포인트 전액 회수 및 취약점 패치 완료했습니다.', '2024-04-14 09:10:00', '2024-04-14 11:30:00'),
                                                                                                                                        (15, '기타 신고', '확인 부탁드립니다', 10, 'RESOLVED', '건의해주신 내용은 개발팀에 전달하여 추후 반영하도록 하겠습니다.', '2024-04-15 16:00:00', '2024-04-16 09:20:00');

-- 17. report_count
INSERT INTO report_count (report_count_no, report_count, user_no) VALUES
                                                                      (1, 1, 1), (2, 2, 2), (3, 0, 3), (4, 1, 4), (5, 0, 5),
                                                                      (6, 3, 6), (7, 1, 7), (8, 0, 8), (9, 2, 9), (10, 0, 10),
                                                                      (11, 1, 11), (12, 0, 12), (13, 4, 13), (14, 0, 14), (15, 1, 15);

SET FOREIGN_KEY_CHECKS = 1; -- 외래키 제약조건 복구