CREATE TABLE IF NOT EXISTS reservation
(
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255)    NOT NULL,
    datetime    DATETIME        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS reservation_time
(
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    start_at    VARCHAR(255)    NOT NULL,
    PRIMARY KEY (id)
);