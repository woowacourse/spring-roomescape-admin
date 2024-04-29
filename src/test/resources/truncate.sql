DELETE FROM reservation;
DELETE FROM reservation_time;
ALTER TABLE reservation ALTER COLUMN id RESTART;
ALTER TABLE reservation_time ALTER COLUMN id RESTART;
