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

INSERT INTO client ( id, name )
VALUES ( 1, 'Banana Yoshimoto' );

INSERT INTO address ( id, street, client_id )
VALUES ( 1, 'Beyoglu str', 1 );

INSERT INTO phone ( id, number, client_id )
VALUES ( 1, '8 800 555 35 35', 1 );
