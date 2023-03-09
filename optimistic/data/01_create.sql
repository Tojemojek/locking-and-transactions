create table account
(
    id            bigint not null primary key,
    amount        numeric(19, 2),
    bid           varchar(255) unique,
    created_date  timestamp DEFAULT now(),
    last_modified timestamp DEFAULT now(),
    version       bigint default 0
);

create table address
(
    id            bigint not null primary key,
    bid           varchar(255) unique,
    city          varchar(255),
    street        varchar(255),
    street_no     bigint,
    created_date  timestamp DEFAULT now(),
    last_modified timestamp DEFAULT now(),
    version       bigint default 0
);

create table person
(
    id                 bigint not null primary key,
    bid                varchar(255) unique,
    name               varchar(255),
    primary_address_id bigint
        constraint fk_person_address references address,
    created_date  timestamp DEFAULT now(),
    last_modified timestamp DEFAULT now(),
    version       bigint default 0
);

create table person_accounts
(
    person_id   bigint not null
        constraint fk_pa_person references person,
    accounts_id bigint not null
        constraint fk_pa_account references account
);


create table person_addresses
(
    person_id    bigint not null
        constraint fk_pad_person references person,
    addresses_id bigint not null
        constraint fk_pad_address references address
);

create sequence hibernate_sequence;

alter table person
    owner to isolation;
alter table account
    owner to isolation;
alter table address
    owner to isolation;
alter table person_addresses
    owner to isolation;
alter table person_accounts
    owner to isolation;

alter sequence hibernate_sequence
    owner to isolation;
