truncate table reservation;
ALTER TABLE reservation
    ALTER COLUMN id RESTART WITH 1;
