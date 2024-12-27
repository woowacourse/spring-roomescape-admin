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

insert into reservation (name, date, time_id, theme_id)
values ('reservator1', '2100-12-01', 1, 1);
-- 같은 날짜, 같은 시간, 다른 테마
insert into reservation (name, date, time_id, theme_id)
values ('reservator2', '2100-12-01', 1, 2);
-- 같은 날짜, 다른 시간, 같은 테마
insert into reservation (name, date, time_id, theme_id)
values ('reservator3', '2100-12-01', 2, 1);
