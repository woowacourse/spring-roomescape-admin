DELETE FROM reservation;
ALTER TABLE reservation ALTER COLUMN id RESTART;
INSERT INTO reservation (name, date, time)
VALUES ('브라운', '2024-05-04', '16:00'),
       ('엘라', '2024-05-04', '17:00'),
       ('릴리', '2023-08-05', '15:40');

DELETE  FROM reservation_time;
ALTER TABLE reservation_time ALTER COLUMN id RESTART;
INSERT INTO reservation_time (start_at)
VALUES ('10:00'),
       ('11:00');
