CREATE TABLE IF NOT EXISTS reservation
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    date_time TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);

