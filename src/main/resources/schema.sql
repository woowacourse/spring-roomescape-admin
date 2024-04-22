CREATE TABLE reservation
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name varchar(255) not null,
    date varchar(255) not null,
    time varchar(255) not null,
    primary key (id)
);
CREATE TABLE reservation_time
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
