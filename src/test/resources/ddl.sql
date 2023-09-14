drop table if exists public.demo;
create table public.demo(
    id bigint not null primary key,
    boolean_type_field bytea,
    boolean_field bytea,
    byte_type_field bytea,
    byte_field bytea,
    short_type_field bytea,
    short_field bytea,
    integer_field bytea,
    int_field bytea,
    long_type_field bytea,
    long_field bytea,
    float_type_field bytea,
    float_field bytea,
    double_type_field bytea,
    double_field bytea,
    string_field bytea,
    big_integer_field bytea,
    big_decimal_field bytea,
    date_type_field bytea,
    date_field bytea,
    time_field bytea,
    timestamp_field bytea,
    instant_field bytea,
    local_date_time_field bytea,
    local_date_field bytea,
    local_time_field bytea,
    offset_date_time_field bytea,
    offset_time_field bytea,
    zoned_date_time_field bytea,
    month_field bytea,
    year_field bytea,
    year_month_field bytea,
    byte_array_field bytea
);