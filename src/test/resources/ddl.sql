drop table if exists public.user;
create table public.user(
    id bigint not null primary key,
    username varchar(32),
    password bytea not null,
    phone bytea not null,
    realname bytea,
    identity_card_number bytea not null,
    created_time timestamp,
    last_modified_time timestamp
);