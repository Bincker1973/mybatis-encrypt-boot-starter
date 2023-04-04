create table public.user(
    id bigint not null primary key,
    username varchar(32),
    password bytea not null,
    phone bytea not null,
    realname varchar(32),
    identity_car_number bytea not null,
    create_time timestamp,
    last_modified_time timestamp
);