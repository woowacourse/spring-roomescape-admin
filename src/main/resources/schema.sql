CREATE TABLE reservation_time
(
    id   BIGINT NOT NULL AUTO_INCREMENT,
    start_at TIME   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    name                VARCHAR(255) NOT NULL,
    date                DATE         NOT NULL,
    time                TIME         NOT NULL,
    reservation_time_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_reservation_time
        FOREIGN KEY (reservation_time_id)
            REFERENCES reservation_time (id)
);