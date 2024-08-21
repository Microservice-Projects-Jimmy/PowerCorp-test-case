create table codes
(
    id serial primary key,
    username varchar(50),
    code varchar(50) unique
);