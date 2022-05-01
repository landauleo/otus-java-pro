CREATE TABLE test
(
    id   int,
    name varchar(50)
);
CREATE TABLE client
(
    id   bigserial NOT NULL PRIMARY KEY,
    name varchar(50)
);
CREATE TABLE manager
(
    no     bigserial NOT NULL PRIMARY KEY,
    label  varchar(50),
    param1 varchar(50)
);