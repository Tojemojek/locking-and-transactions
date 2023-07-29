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

create table person_addresses
(
    person_id    bigint not null
        constraint fk_pad_person references person,
    addresses_id bigint not null
        constraint fk_pad_address references address
);

create sequence address_seq increment by 50;;
create sequence person_seq increment by 50;;

alter table person
    owner to isolation;
alter table address
    owner to isolation;
alter table person_addresses
    owner to isolation;

alter sequence address_seq
    owner to isolation;

alter sequence person_seq
    owner to isolation;
