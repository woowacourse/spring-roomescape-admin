insert into reservation_time (start_at)
values ('10:00');
insert into reservation_time (start_at)
values ('11:00');
-- 아직 예약이 없는 시간
insert into reservation_time (start_at)
values ('12:00');

insert into theme (name, description, thumbnail)
values ('theme1', 'none', '/image/house.png');
insert into theme (name, description, thumbnail)
values ('theme2', 'none', '/image/thief.png');
-- 아직 예약이 없는 테마
insert into theme (name, description, thumbnail)
values ('no_reservation_theme', 'none', '/image/floors.png');

insert into reservation (name, date, time_id, theme_id)
values ('reservator1', CURRENT_DATE - 1, 1, 1);
-- 같은 날짜, 같은 시간, 다른 테마
insert into reservation (name, date, time_id, theme_id)
values ('reservator2', CURRENT_DATE - 1, 1, 2);
-- 같은 날짜, 다른 시간, 같은 테마
insert into reservation (name, date, time_id, theme_id)
values ('reservator3', CURRENT_DATE - 1, 2, 1);
