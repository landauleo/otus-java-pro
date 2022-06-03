CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE address
(
    id     bigint NOT NULL PRIMARY KEY,
    street varchar(100)
);

CREATE TABLE client
(
    id         bigint NOT NULL PRIMARY KEY,
    name       varchar(50),
    address_id bigint REFERENCES address (id)
);

CREATE TABLE phone
(
    id        bigint NOT NULL PRIMARY KEY,
    number    varchar(50),
    client_id bigint NOT NULL REFERENCES client (id)
);

