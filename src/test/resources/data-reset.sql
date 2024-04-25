DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS reservation_time;


CREATE TABLE IF NOT EXISTS reservation_time (
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS reservation (
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date VARCHAR(255) NOT NULL,
    time_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
);


INSERT INTO reservation_time (start_at) values ('23:59');
INSERT INTO reservation_time (start_at) values ('00:00');
INSERT INTO reservation (name, date, time_id) values ( '브라운', '9999-12-31', '1' );
