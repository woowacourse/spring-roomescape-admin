insert into reservation_time (start_at)
values ('10:00');
insert into reservation_time (start_at)
values ('11:00');
-- 아직 예약이 없는 시간
insert into reservation_time (start_at)
values ('12:00');

insert into theme (name, description, thumbnail)
values ('theme1', 'none', 'none');
insert into theme (name, description, thumbnail)
values ('theme2', 'none', 'none');
-- 아직 예약이 없는 테마
insert into theme (name, description, thumbnail)
values ('no_reservation_theme', 'none', 'none');

insert into member(name, email, password, role)
values ('kargo', 'kargo@google.com', '1234', 'ADMIN');
insert into member(name, email, password, role)
values ('solar', 'solar@google.com', '1234', 'USER');
insert into member(name, email, password, role)
values ('hotea', 'hotea@google.com', '1234', 'USER');

insert into reservation (member_id, date, time_id, theme_id)
values (1, '2100-12-01', 1, 1);

-- 1번 예약과 비교했을 때, 같은 날짜, 같은 시간, 다른 테마
insert into reservation (member_id, date, time_id, theme_id)
values (2, '2100-12-01', 1, 2);

-- 1번 예약과 비교했을 때, 같은 날짜, 다른 시간, 같은 테마
insert into reservation (member_id, date, time_id, theme_id)
values (3, '2100-12-01', 2, 1);
