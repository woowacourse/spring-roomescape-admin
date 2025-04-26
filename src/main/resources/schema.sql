CREATE TABLE reservation_time
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
);

INSERT INTO reservation_time (start_at)
SELECT '10:00'
WHERE NOT EXISTS (SELECT 1
                  FROM reservation_time
                  WHERE start_at = '10:00');

INSERT INTO reservation_time (start_at)
SELECT '11:00'
WHERE NOT EXISTS (SELECT 1
                  FROM reservation_time
                  WHERE start_at = '11:00');

