CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE customers
(
    id         SERIAL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);
