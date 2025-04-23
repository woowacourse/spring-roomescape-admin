DROP TABLE IF EXISTS reservation;

CREATE TABLE reservation
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    datetime DATETIME     NOT NULL,
    PRIMARY KEY (id)
);
