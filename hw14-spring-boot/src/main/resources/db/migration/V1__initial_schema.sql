CREATE TABLE client
(
    id   bigserial NOT NULL PRIMARY KEY,
    name varchar(50)
);

CREATE TABLE address
(
    id        bigserial NOT NULL PRIMARY KEY,
    street    varchar(100),
    client_id bigint    NOT NULL REFERENCES client (id)
);

CREATE TABLE phone
(
    id        bigserial NOT NULL PRIMARY KEY,
    number    varchar(50),
    client_id bigint    NOT NULL REFERENCES client (id)
);
