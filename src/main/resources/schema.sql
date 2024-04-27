CREATE TABLE reservation_time
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_at VARCHAR(255) NOT NULL
);

CREATE TABLE reservation
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date VARCHAR(255) NOT NULL,
    time_id BIGINT,
    FOREIGN KEY (time_id) REFERENCES reservation_time(id)
);
