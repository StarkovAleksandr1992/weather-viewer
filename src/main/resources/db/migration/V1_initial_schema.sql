create schema if not exists weather_viewer;

create table weather_viewer.customer_info
(
    customer_id bigint not null,
    login       varchar(50),
    password    varchar(50),
    expires     timestamp(6),
    primary key (customer_id)
);

create table weather_viewer.customer_locations
(
    customer_id bigint not null,
    name        varchar(255),
    latitude    numeric(38, 2),
    longitude   numeric(38, 2),
    constraint fk foreign key (customer_id) references weather_viewer.customer_info
);

create sequence if not exists weather_viewer.customer_info_seq;

