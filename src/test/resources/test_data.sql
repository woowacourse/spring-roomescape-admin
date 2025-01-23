-- reservation_time 데이터 삽입
insert into reservation_time (start_at, created_date, last_modified_date)
values ('10:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into reservation_time (start_at, created_date, last_modified_date)
values ('11:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 아직 예약이 없는 시간
insert into reservation_time (start_at, created_date, last_modified_date)
values ('12:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- theme 데이터 삽입
insert into theme (name, description, thumbnail, created_date, last_modified_date)
values ('theme1', 'none', 'none', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into theme (name, description, thumbnail, created_date, last_modified_date)
values ('theme2', 'none', 'none', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 아직 예약이 없는 테마
insert into theme (name, description, thumbnail, created_date, last_modified_date)
values ('no_reservation_theme', 'none', 'none', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- reservation_slot 데이터 삽입
insert into reservation_slot (date, time_id, theme_id, created_date, last_modified_date)
values ('2100-12-01', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into reservation_slot (date, time_id, theme_id, created_date, last_modified_date)
values ('2100-12-01', 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into reservation_slot (date, time_id, theme_id, created_date, last_modified_date)
values ('2100-12-01', 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- member 데이터 삽입
insert into member (name, email, password, role, created_date, last_modified_date)
values ('kargo', 'kargo@google.com', '1234', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into member (name, email, password, role, created_date, last_modified_date)
values ('solar', 'solar@google.com', '1234', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into member (name, email, password, role, created_date, last_modified_date)
values ('hotea', 'hotea@google.com', '1234', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- reservation 데이터 삽입
insert into reservation (member_id, slot_id, status, created_date, last_modified_date)
values (1, 1, 'RESERVED', DATEADD(SECOND, -100, CURRENT_TIMESTAMP), DATEADD(SECOND, -100, CURRENT_TIMESTAMP));

-- 같은 날짜, 같은 시간, 다른 테마
insert into reservation (member_id, slot_id, status, created_date, last_modified_date)
values (2, 2, 'RESERVED', DATEADD(SECOND, -99, CURRENT_TIMESTAMP), DATEADD(SECOND, -99, CURRENT_TIMESTAMP));

-- 같은 날짜, 다른 시간, 같은 테마
insert into reservation (member_id, slot_id, status, created_date, last_modified_date)
values (3, 3, 'RESERVED', DATEADD(SECOND, -98, CURRENT_TIMESTAMP), DATEADD(SECOND, -98, CURRENT_TIMESTAMP));

-- WAITING 상태 데이터
insert into reservation (member_id, slot_id, status, created_date, last_modified_date)
values (2, 1, 'WAITING', DATEADD(SECOND, -97, CURRENT_TIMESTAMP), DATEADD(SECOND, -97, CURRENT_TIMESTAMP));

insert into reservation (member_id, slot_id, status, created_date, last_modified_date)
values (3, 1, 'WAITING', DATEADD(SECOND, -96, CURRENT_TIMESTAMP), DATEADD(SECOND, -96, CURRENT_TIMESTAMP));
