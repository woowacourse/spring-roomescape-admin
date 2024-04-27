DROP TABLE reservation;
DROP TABLE reservation_time;

CREATE TABLE reservation_time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date VARCHAR(255) NOT NULL,
    time_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
);

INSERT INTO reservation_time (start_at) VALUES ('10:05:00'), ('09:15:00'), ('12:30:00');
INSERT INTO reservation (name, date, time_id) VALUES ('first', '2024-05-10', 1), ('second', '2024-10-06', 2);
