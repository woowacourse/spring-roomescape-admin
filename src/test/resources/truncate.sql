truncate table reservation;
ALTER TABLE reservation
    ALTER COLUMN id RESTART WITH 1;

truncate table reservation_time;
ALTER TABLE reservation_time
    ALTER COLUMN id RESTART WITH 1;
