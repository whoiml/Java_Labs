create table users(
    id serial primary key,
    username varchar(128) not null unique,
    email varchar(128) not null unique,
    name varchar(128) not null,
    password varchar(128) not null,
    role int not null
);

create table ad(
    id serial primary key,
    user_id int not null,
    ad_name varchar(32) not null,
    description varchar(128) not null,
    creation_date timestamp,
    theme varchar(32)
);