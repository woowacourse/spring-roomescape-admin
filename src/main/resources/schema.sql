create table if not exists time_slot
(
    id       bigint not null auto_increment,
    start_at time   not null,
    primary key (id)
);

create table if not exists reservation
(
    id           bigint       not null auto_increment,
    name         varchar(255) not null,
    date         date         not null,
    time_slot_id bigint       not null,
    primary key (id),
    foreign key (time_slot_id) references time_slot (id)
);
