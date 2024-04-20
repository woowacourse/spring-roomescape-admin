create table if not exists reservation_time
(
    id       bigint not null auto_increment,
    start_at time   not null,
    primary key (id)
);

create table if not exists reservation
(
    id      bigint       not null auto_increment,
    name    varchar(255) not null,
    date    date         not null,
    time_id bigint,
    primary key (id),
    foreign key (time_id) references reservation_time (id)
);
