DELETE FROM reservation;
DELETE FROM reservation_time;
INSERT INTO reservation_time (start_at) VALUES ('10:05:00'), ('09:15:00'), ('12:30:00');
INSERT INTO reservation (name, date, time_id) VALUES ('first', '2024-05-10', 1), ('second', '2024-10-06', 2);
