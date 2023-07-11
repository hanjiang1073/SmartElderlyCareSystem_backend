create table elder
(
    elderid     int auto_increment
        primary key,
    name        varchar(16) null,
    age         int         null,
    phone       varchar(16) null,
    description varchar(16) null
);

create table event
(
    event_id int auto_increment
        primary key,
    type     int         null,
    `desc`   varchar(64) null
);

create table role
(
    name   enum ('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN') default 'ROLE_USER' not null,
    roleid int                                                                    not null
        primary key
);

create table user
(
    id       int auto_increment
        primary key,
    username varchar(500) null,
    password varchar(500) null
);

create table userrole
(
    id     mediumtext null,
    roleid mediumtext null
);

create table volunteer
(
    volunteer_id int auto_increment
        primary key,
    name         varchar(16) null,
    age          int         null,
    phone        varchar(16) null,
    description  varchar(16) null
);


