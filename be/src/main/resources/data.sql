-- 관리자
insert into member (id, member_id, password, name, email, birthday, point, role) values (1, 'admin', '$2a$10$tJSm6EfLNxha1.8CrAnJIOeaeC7oUo518GYPJKZiC3rx3rjg41KJa', '관리자', 'unabbs1@sfgate.com', '2023-09-08', 53, 'ROLE_ADMIN');

-- 조원 데이터
insert into member (id, member_id, password, name, email, birthday, point, role, profile_url)
values (2, 'tjgml1 ', '$2a$10$a6OcTpLzJ4Yq9vSWKVeJSOtVUSASArGKv5RcAtWHDHdIJsTg2cpN.', '정서희', 'tjgml1@naver.com', '1999-04-04', 128, 'ROLE_STUDENT',
        "https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/5c699caa-d131-42bd-beac-fa5322c75f43.jpg");
insert into member (id, member_id, password, name, email, birthday, point, role, profile_url)
values (3, 'rmsdud1', '$2a$10$a6OcTpLzJ4Yq9vSWKVeJSOtVUSASArGKv5RcAtWHDHdIJsTg2cpN.', '최근영', 'rmsdud1@naver.com', '1996-05-04', 65, 'ROLE_STUDENT',
        "https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/5c699caa-d131-42bd-beac-fa5322c75f43.jpg");
insert into member (id, member_id, password, name, email, birthday, point, role, profile_url)
values (4, 'dudqls1', '$2a$10$a6OcTpLzJ4Yq9vSWKVeJSOtVUSASArGKv5RcAtWHDHdIJsTg2cpN.', '전영빈', 'dudqls1@naver.com', '1998-02-16', 888, 'ROLE_STUDENT',
        "https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/5c699caa-d131-42bd-beac-fa5322c75f43.jpg");
insert into member (id, member_id, password, name, email, birthday, point, role, profile_url)
values (5, 'wotjd1', '$2a$10$a6OcTpLzJ4Yq9vSWKVeJSOtVUSASArGKv5RcAtWHDHdIJsTg2cpN.', '장재성', 'wotjd1@naver.com', '1998-11-10', 751, 'ROLE_STUDENT',
        "https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/5c699caa-d131-42bd-beac-fa5322c75f43.jpg");
insert into member (id, member_id, password, name, email, birthday, point, role, profile_url)
values (6, 'wotjs1', '$2a$10$a6OcTpLzJ4Yq9vSWKVeJSOtVUSASArGKv5RcAtWHDHdIJsTg2cpN.', '박재선', 'wotjs1@naver.com', '1995-09-04', 654, 'ROLE_STUDENT',
        "https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/5c699caa-d131-42bd-beac-fa5322c75f43.jpg");
insert into member (id, member_id, password, name, email, birthday, point, role, profile_url)
values (7, 'ckdgh1', '$2a$10$a6OcTpLzJ4Yq9vSWKVeJSOtVUSASArGKv5RcAtWHDHdIJsTg2cpN.', '최창호', 'ckdgh1@naver.com', '1994-06-04', 456, 'ROLE_STUDENT',
        "https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/5c699caa-d131-42bd-beac-fa5322c75f43.jpg");

-- 선생님
insert into member (id, member_id, password, name, email, birthday, point, role) values (8, 'teacher', '$2a$10$tJSm6EfLNxha1.8CrAnJIOeaeC7oUo518GYPJKZiC3rx3rjg41KJa', '김대성', 'unabbs1@sfgate.com', '2023-09-08', 53, 'ROLE_TEACHER');

-- 멘토 id
insert into member (id, member_id, password, name, email, birthday, point, role) values (9, 'mentor1', '$2a$10$tJSm6EfLNxha1.8CrAnJIOeaeC7oUo518GYPJKZiC3rx3rjg41KJa', '장대성', 'unabbs1@sfgate.com', '2023-09-08', 53, 'ROLE_MENTOR');
insert into member (id, member_id, password, name, email, birthday, point, role) values (10, 'mentor2', '$2a$10$tJSm6EfLNxha1.8CrAnJIOeaeC7oUo518GYPJKZiC3rx3rjg41KJa', '이병옥', 'unabbs1@sfgate.com', '2023-09-08', 53, 'ROLE_MENTOR');


insert into attendance (status, check_in, check_out, id, member_id)
values (0, '2024-02-01 09:48:34', '2024-02-01 18:48:34', 1, 4);
insert into attendance (status, check_in, check_out, id, member_id)
values (1, '2024-02-02 09:48:34', '2024-02-02 18:48:34', 2, 4);
insert into attendance (status, check_in, check_out, id, member_id)
values (1, '2024-02-03 09:48:34', '2024-02-03 18:48:34', 3, 4);
insert into attendance (status, check_in, check_out, id, member_id)
values (2, '2024-02-04 09:48:34', '2024-02-04 18:48:34', 4, 4);
insert into attendance (status, check_in, check_out, id, member_id)
values (2, '2024-02-05 09:48:34', '2024-02-05 18:48:34', 5, 4);
insert into attendance (status, check_in, check_out, id, member_id)
values (0, '2024-02-06 09:48:34', '2024-02-06 18:48:34', 6, 4);
insert into attendance (status, check_in, check_out, id, member_id)
values (0, '2024-02-07 09:48:34', '2024-02-07 18:48:34', 7, 4);
insert into attendance (status, check_in, check_out, id, member_id)
values (0, '2024-02-08 09:48:34', '2024-02-08 18:48:34', 8, 4);
insert into attendance (status, check_in, check_out, id, member_id)
values (0, '2024-02-09 09:48:34', '2024-02-09 18:48:34', 9, 4);
insert into attendance (status, check_in, check_out, id, member_id)
values (3, '2024-02-10 09:48:34', '2024-02-10 18:48:34', 10, 4);
insert into attendance (status, check_in, check_out, id, member_id)
values (3, '2024-02-11 09:48:34', '2024-02-11 18:48:34', 11, 4);

insert into badge (id, image_url)
values (1, "https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/e1393253-70bb-43df-91c0-eb08cf940141.png");
insert into badge (id, image_url)
values (2, "https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/38ae118f-ca62-45a6-b591-376bbce43bdc.png");
insert into badge (id, image_url)
values (3, "https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/ec37c826-aece-46ff-8372-775b1104c4e2.png");
insert into badge (id, image_url)
values (4, "https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/484b859a-413a-42aa-98bb-50e7d51c4508.png");
insert into badge (id, image_url)
values (5, "https://otc-image-bucket.s3.ap-northeast-2.amazonaws.com/681b5e23-b73a-4441-8b40-2853d52dbede.png");


insert into member_badge(member_id, badge_id)
values (4, 1);
insert into member_badge(member_id, badge_id)
values (4, 2);

-- 반 정보
insert into classroom (id, year, grade, number) values (1, 2024, 1, 1);
insert into classroom (id, year, grade, number) values (2, 2024, 1, 2);
insert into classroom (id, year, grade, number) values (3, 2024, 1, 3);
insert into classroom (id, year, grade, number) values (4, 2024, 1, 4);
insert into classroom (id, year, grade, number) values (5, 2024, 1, 5);
insert into classroom (id, year, grade, number) values (6, 2024, 2, 1);
insert into classroom (id, year, grade, number) values (7, 2024, 2, 2);
insert into classroom (id, year, grade, number) values (8, 2024, 2, 3);
insert into classroom (id, year, grade, number) values (9, 2024, 2, 4);
insert into classroom (id, year, grade, number) values (10, 2024, 2, 5);
insert into classroom (id, year, grade, number) values (11, 2024, 3, 1);
insert into classroom (id, year, grade, number) values (12, 2024, 3, 2);
insert into classroom (id, year, grade, number) values (13, 2024, 3, 3);
insert into classroom (id, year, grade, number) values (14, 2024, 3, 4);
insert into classroom (id, year, grade, number) values (15, 2024, 3, 5);
insert into classroom (id, year, grade, number) values (16, 2024, 4, 1);
insert into classroom (id, year, grade, number) values (17, 2024, 4, 2);
insert into classroom (id, year, grade, number, room_url) values (18, 2024, 4, 3, "https://our-tiny-classroom.daily.co/2024-4-3");
insert into classroom (id, year, grade, number) values (19, 2024, 4, 4);
insert into classroom (id, year, grade, number) values (20, 2024, 4, 5);
insert into classroom (id, year, grade, number) values (21, 2024, 5, 1);
insert into classroom (id, year, grade, number) values (22, 2024, 5, 2);
insert into classroom (id, year, grade, number) values (23, 2024, 5, 3);
insert into classroom (id, year, grade, number) values (24, 2024, 5, 4);
insert into classroom (id, year, grade, number) values (25, 2024, 5, 5);
insert into classroom (id, year, grade, number) values (26, 2024, 6, 1);
insert into classroom (id, year, grade, number) values (27, 2024, 6, 2);
insert into classroom (id, year, grade, number) values (28, 2024, 6, 3);
insert into classroom (id, year, grade, number) values (29, 2024, 6, 4);
insert into classroom (id, year, grade, number) values (30, 2024, 6, 5);
insert into classroom (id, year, grade, number) values (31, 2024, 7, 1);
insert into classroom (id, year, grade, number) values (32, 2024, 7, 2);
insert into classroom (id, year, grade, number) values (33, 2024, 7, 3);
insert into classroom (id, year, grade, number) values (34, 2024, 7, 4);
insert into classroom (id, year, grade, number) values (35, 2024, 7, 5);
insert into classroom (id, year, grade, number) values (36, 2024, 8, 1);
insert into classroom (id, year, grade, number) values (37, 2024, 8, 2);
insert into classroom (id, year, grade, number) values (38, 2024, 8, 3);
insert into classroom (id, year, grade, number) values (39, 2024, 8, 4);
insert into classroom (id, year, grade, number) values (40, 2024, 8, 5);
insert into classroom (id, year, grade, number) values (41, 2024, 9, 1);
insert into classroom (id, year, grade, number) values (42, 2024, 9, 2);
insert into classroom (id, year, grade, number) values (43, 2024, 9, 3);
insert into classroom (id, year, grade, number) values (44, 2024, 9, 4);
insert into classroom (id, year, grade, number) values (45, 2024, 9, 5);
insert into classroom (id, year, grade, number) values (46, 2024, 10, 1);
insert into classroom (id, year, grade, number) values (47, 2024, 10, 2);
insert into classroom (id, year, grade, number) values (48, 2024, 10, 3);
insert into classroom (id, year, grade, number) values (49, 2024, 10, 4);
insert into classroom (id, year, grade, number) values (50, 2024, 10, 5);
insert into classroom (id, year, grade, number) values (51, 2024, 11, 1);
insert into classroom (id, year, grade, number) values (52, 2024, 11, 2);
insert into classroom (id, year, grade, number) values (53, 2024, 11, 3);
insert into classroom (id, year, grade, number) values (54, 2024, 11, 4);
insert into classroom (id, year, grade, number) values (55, 2024, 11, 5);
insert into classroom (id, year, grade, number) values (56, 2024, 12, 1);
insert into classroom (id, year, grade, number) values (57, 2024, 12, 2);
insert into classroom (id, year, grade, number) values (58, 2024, 12, 3);
insert into classroom (id, year, grade, number) values (59, 2024, 12, 4);
insert into classroom (id, year, grade, number) values (60, 2024, 12, 5);

insert into classroom (id, year, grade, number)
values (61, 2024, 1, 0);
insert into classroom (id, year, grade, number)
values (62, 2024, 2, 0);
insert into classroom (id, year, grade, number)
values (63, 2024, 3, 0);
insert into classroom (id, year, grade, number)
values (64, 2024, 4, 0);
insert into classroom (id, year, grade, number)
values (65, 2024, 5, 0);
insert into classroom (id, year, grade, number)
values (66, 2024, 6, 0);
insert into classroom (id, year, grade, number)
values (67, 2024, 7, 0);
insert into classroom (id, year, grade, number)
values (68, 2024, 8, 0);
insert into classroom (id, year, grade, number)
values (69, 2024, 9, 0);
insert into classroom (id, year, grade, number)
values (70, 2024, 10, 0);
insert into classroom (id, year, grade, number)
values (71, 2024, 11, 0);
insert into classroom (id, year, grade, number)
values (72, 2024, 12, 0);

-- 반 매핑
INSERT INTO member_class_room (`class_room_id`, `member_id`)
VALUES ('18', '2');
INSERT INTO member_class_room (`class_room_id`, `member_id`)
VALUES ('18', '3');
INSERT INTO member_class_room (`class_room_id`, `member_id`)
VALUES ('18', '4');
INSERT INTO member_class_room (`class_room_id`, `member_id`)
VALUES ('18', '5');
INSERT INTO member_class_room (`class_room_id`, `member_id`)
VALUES ('18', '6');
INSERT INTO member_class_room (`class_room_id`, `member_id`)
VALUES ('18', '7');
-- 담임 선생님
INSERT INTO member_class_room (`class_room_id`, `member_id`)
VALUES ('18', '8');

-- 자유강의
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`)
VALUES ('0', '0', '1', '모션 그래픽 공부', '모션 그래픽', 'ART', 'FREE_LECTURE', 'APPROVED');
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`)
VALUES ('0', '0', '1', '꽃꽃꽃꽃', '꽃꽂이', 'ART', 'FREE_LECTURE', 'APPROVED');
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`)
VALUES ('0', '0', '1', '네일아트 공부', '네일아트', 'ART', 'FREE_LECTURE', 'APPROVED');
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`)
VALUES ('0', '0', '1', '피아노피아노', '피아노', 'MUSIC', 'FREE_LECTURE', 'APPROVED');
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`)
VALUES ('0', '0', '1', '클래식 감상', '고전 음악의 이해', 'MUSIC', 'FREE_LECTURE', 'APPROVED');
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`)
VALUES ('0', '0', '1', '한시간동안 기타 연습', '기타 연주', 'MUSIC', 'FREE_LECTURE', 'APPROVED');
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`)
VALUES ('0', '0', '1', '백준에서 실버 한문제 풀기', '알고리즘', 'MATH', 'FREE_LECTURE', 'APPROVED');
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`)
VALUES ('0', '0', '1', '미분과 적분 개념정리', '미적분학', 'MATH', 'FREE_LECTURE', 'APPROVED');
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`)
VALUES ('0', '0', '1', '체스를 같이 할 친구가 있으신가요?', '체스', 'HOBBY', 'FREE_LECTURE', 'APPROVED');
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`)
VALUES ('0', '0', '1', '장기를 같이 할 친구가 있으신가요?', '장기', 'HOBBY', 'FREE_LECTURE', 'APPROVED');

-- 정규강의
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`)
VALUES ('0', '0', '1', '수학수학', '수학', 'MATH', 'REGULAR_LECTURE', 'APPROVED');
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`)
VALUES ('0', '0', '1', '영어영어', '영어', 'ENGLISH', 'REGULAR_LECTURE', 'APPROVED');
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`)
VALUES ('0', '0', '1', '도덕도덕', '도덕', 'ETHICS', 'REGULAR_LECTURE', 'APPROVED');
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`)
VALUES ('0', '0', '1', '국어', '고전문학', 'KOREAN', 'REGULAR_LECTURE', 'APPROVED');

-- 특수강의
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`, `date`)
VALUES ('3', '5', '9', '갈등 해결을 위한 대화법', '갈등해결을 위한 대화기술', 'NO_CATEGORY', 'SPECIAL_LECTURE', 'APPROVED', '2024-02-15');
INSERT INTO lecture (`day_of_week`, `time_table`, `member_id`, `description`, `title`, `lecture_category_type`, `lecture_type`, `approved`, `date`)
VALUES ('4', '5', '10', '신뢰를 얻는 대화법', '대화의 심리학: 소통에서 신뢰로', 'NO_CATEGORY', 'SPECIAL_LECTURE', 'APPROVED', '2024-02-16');

-- 스케줄
INSERT INTO schedule (`schedule_date`, `lecture_id`, `member_id`, `deletable`, `day_of_week`, `time_table`)
VALUES ('2024-02-14', '12', '8', false, '2', '3');
INSERT INTO schedule (`schedule_date`, `lecture_id`, `member_id`, `deletable`, `day_of_week`, `time_table`)
VALUES ('2024-02-14', '11', '8', false, '2', '4');
INSERT INTO schedule (`schedule_date`, `lecture_id`, `member_id`, `deletable`, `day_of_week`, `time_table`)
VALUES ('2024-02-13', '13', '8', false, '1', '3');
INSERT INTO schedule (`schedule_date`, `lecture_id`, `member_id`, `deletable`, `day_of_week`, `time_table`)
VALUES ('2024-02-13', '14', '8', false, '1', '4');
INSERT INTO schedule (`schedule_date`, `lecture_id`, `member_id`, `deletable`, `day_of_week`, `time_table`)
VALUES ('2024-02-15', '15', '8', false, '3', '5');


-- 멤버 스케줄
INSERT INTO member_schedule (`lecture_category_type`, `member_id`, `schedule_id`)
values ('MATH', '4', '1');
INSERT INTO member_schedule (`lecture_category_type`, `member_id`, `schedule_id`)
values ('ENGLISH', '4', '2');
INSERT INTO member_schedule (`lecture_category_type`, `member_id`, `schedule_id`)
values ('ETHICS', '4', '3');
INSERT INTO member_schedule (`lecture_category_type`, `member_id`, `schedule_id`)
values ('KOREAN', '4', '4');

