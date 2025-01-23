drop table if exists reservation;
drop table if exists reservation_slot;
drop table if exists reservation_time;
drop table if exists theme;
drop table if exists member;

CREATE TABLE theme
(
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    name               VARCHAR(255) NOT NULL,
    description        VARCHAR(255) NOT NULL,
    thumbnail          VARCHAR(255) NOT NULL,
    created_date       timestamp,
    last_modified_date timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE reservation_time
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    start_at           TIME   NOT NULL,
    created_date       timestamp,
    last_modified_date timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    name               VARCHAR(255) NOT NULL,
    email              VARCHAR(255) NOT NULL UNIQUE,
    password           VARCHAR(255) NOT NULL,
    role               VARCHAR(255) NOT NULL,
    created_date       timestamp,
    last_modified_date timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE reservation_slot
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    date               DATE   NOT NULL,
    time_id            BIGINT NOT NULL,
    theme_id           BIGINT NOT NULL,
    created_date       timestamp,
    last_modified_date timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id),
    FOREIGN KEY (theme_id) REFERENCES theme (id),
    UNIQUE (date, time_id, theme_id)
);

CREATE TABLE reservation
(
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    member_id          BIGINT       NOT NULL,
    slot_id            BIGINT       NOT NULL,
    status             VARCHAR(255) NOT NULL,
    created_date       timestamp,
    last_modified_date timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (slot_id) REFERENCES reservation_slot (id)
);
