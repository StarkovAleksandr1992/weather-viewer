CREATE DATABASE weather_viewer;
\c weather_viewer;

create schema if not exists weather_viewer;

create table if not exists weather_viewer.t_customers
(
    c_customer_id uuid not null,
    c_login       varchar(60) unique,
    c_password    varchar(60),
    primary key (c_customer_id)
);

create index if not exists ix_customer_login
    on weather_viewer.t_customers (c_login);

create table if not exists weather_viewer.t_locations
(
    c_location_id  uuid not null,
    c_customer_id  uuid not null,
    c_name         varchar(100),
    c_country_code varchar(3),
    c_latitude     float(53),
    c_longitude    float(53),
    primary key (c_location_id),
    unique (c_customer_id, c_name, c_country_code, c_latitude, c_longitude)
);

create index if not exists ix_coordinates
    on weather_viewer.t_locations (c_latitude, c_longitude);

create table if not exists weather_viewer.t_sessions
(
    c_session_id  uuid         not null,
    c_customer_id uuid         not null,
    c_expires_at  timestamp(6) not null,
    c_status      varchar(10)  not null,
    primary key (c_session_id)
);
