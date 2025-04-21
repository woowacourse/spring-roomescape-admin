CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    reservation_date    DATE NOT NULL,
    reservation_time    TIME NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation_time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    start_at TIME NOT NULL,
    PRIMARY KEY (id)
);
