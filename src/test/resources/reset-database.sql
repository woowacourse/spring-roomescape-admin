DROP TABLE reservation;
DROP TABLE reservation_time;

CREATE TABLE reservation_time
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    start_at TIME         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    DATE         NOT NULL,
    time_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
);

INSERT INTO reservation_time(start_at) VALUES ('10:00');
INSERT INTO reservation_time(start_at) VALUES ('11:00');

INSERT INTO reservation(name, date, time_id) VALUES ('미르', '2024-04-24', 1);
INSERT INTO reservation(name, date, time_id) VALUES ('미르', '2024-04-24', 1);
INSERT INTO reservation(name, date, time_id) VALUES ('미르', '2024-04-25', 1);
