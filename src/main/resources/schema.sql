CREATE TABLE IF NOT EXISTS reservation
(
    id      BIGINT       NOT NULL,
    name    VARCHAR(255) NOT NULL,
    date    DATE         NOT NULL,
    time    TIME         NOT NULL,
    PRIMARY KEY (id)
);
