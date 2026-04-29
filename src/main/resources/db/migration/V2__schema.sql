CREATE TABLE reservation_time
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE reservation DROP COLUMN time;
ALTER TABLE reservation ADD COLUMN time_id BIGINT;

ALTER TABLE reservation
    ADD CONSTRAINT fk_reservation_time_id
        FOREIGN KEY (time_id) REFERENCES reservation_time (id);
