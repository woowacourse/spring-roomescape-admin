drop table if exists reservation;
drop table if exists reservation_time;
create table reservation
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date DATE         NOT NULL,
    time TIME         NOT NULL,
    PRIMARY KEY (id)
);

create table reservation_time
(
    id       BIGINT NOT NULL AUTO_INCREMENT,
    start_at TIME   NOT NULL,
    PRIMARY KEY (id)
);
