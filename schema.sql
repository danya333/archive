create table if not exists users
(
    id       serial8,
    name     varchar not null,
    surname  varchar not null,
    role     varchar not null,
    username varchar not null,
    password varchar not null,
    primary key (id)
);


create table if not exists locations
(
    id      serial8,
    country varchar not null,
    city    varchar not null,
    primary key (id)
);


create table if not exists statuses
(
    id   serial8,
    name varchar not null,
    primary key (id)
);


create table if not exists stages
(
    id          serial8,
    name        varchar not null,
    location_id int8    not null,
    status_id   int8,
    primary key (id),
    foreign key (location_id) references locations (id),
    foreign key (status_id) references statuses (id)
);


create table if not exists stages_status
(
    id        serial8,
    stage_id  int8 not null,
    status_id int8 not null,
    primary key (id),
    foreign key (stage_id) references stages (id),
    foreign key (status_id) references statuses (id)
);


create table if not exists years
(
    id   serial8,
    year int4 not null,
    primary key (id)
);


create table if not exists projects
(
    id         serial8,
    name       varchar   not null,
    short_name varchar   not null,
    code       varchar   not null,
    created_at timestamp not null,
    year_id    int8      not null,
    status_id  int8,
    primary key (id),
    foreign key (year_id) references years (id),
    foreign key (status_id) references statuses (id)
);


create table if not exists sections
(
    id         serial8,
    name       varchar   not null,
    short_name varchar   not null,
    code       varchar   not null,
    created_at timestamp not null,
    project_id int8      not null,
    primary key (id),
    foreign key (project_id) references projects (id)
);


create table if not exists files
(
    id                 serial8,
    created_by_user_id int8      not null,
    updated_by_user_id int8      not null,
    section_id         int8      not null,
    status_id          int8      not null,
    name               varchar   not null,
    type               varchar   not null,
    path               varchar   not null,
    weight             int4      not null,
    created_at         timestamp not null,
    updated_at         timestamp not null,
    primary key (id),
    foreign key (created_by_user_id) references users (id),
    foreign key (updated_by_user_id) references users (id),
    foreign key (section_id) references sections (id),
    foreign key (status_id) references statuses (id)
);

