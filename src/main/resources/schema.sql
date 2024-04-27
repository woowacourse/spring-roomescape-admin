CREATE TABLE reservation_time
(
    id       BIGINT NOT NULL AUTO_INCREMENT,
    start_at TIME   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    name                VARCHAR(255) NOT NULL,
    date                DATE         NOT NULL,
    reservation_time_id BIGINT,                                        -- 컬럼 수정
    PRIMARY KEY (id),
    FOREIGN KEY (reservation_time_id) REFERENCES reservation_time (id) -- 외래키 추가
);
