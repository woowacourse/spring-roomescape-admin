SET REFERENTIAL_INTEGRITY FALSE;

truncate table reservation;
ALTER TABLE reservation
    ALTER COLUMN id RESTART WITH 1;

truncate table reservation_time;
ALTER TABLE reservation_time
    ALTER COLUMN id RESTART WITH 1;

truncate table theme;
ALTER TABLE theme
    ALTER COLUMN id RESTART WITH 1;

truncate table member;
ALTER TABLE member
    ALTER COLUMN id RESTART WITH 1;

SET REFERENTIAL_INTEGRITY TRUE;
