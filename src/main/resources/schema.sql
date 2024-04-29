CREATE TABLE reservation_time
(
    id   BIGINT        NOT NULL AUTO_INCREMENT,
    start_at timestamp NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date date         NOT NULL,
    time_id BIGINT    NOT NULL,                           -- 컬럼 수정
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id) -- 외래키 추가
);

