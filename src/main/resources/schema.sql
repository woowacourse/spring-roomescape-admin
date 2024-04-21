CREATE TABLE reservation
(
    id                    BIGINT       NOT NULL AUTO_INCREMENT,
    name                  VARCHAR(255) NOT NULL,
    reservation_date_time DATETIME     NOT NULL,
    PRIMARY KEY (id)
);
