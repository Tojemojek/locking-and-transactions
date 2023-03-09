create table account
(
    id            bigint not null primary key,
    amount        numeric(19, 2),
    bid           varchar(255) unique,
    created_date  timestamp DEFAULT now(),
    last_modified timestamp DEFAULT now(),
    version       bigint default 0
);

create sequence account_seq increment by 50;

alter table account  owner to isolation;

alter sequence account_seq owner to isolation;
