drop table if exists paragraph CASCADE;
CREATE TABLE paragraph (
    id bigint generated by default as identity,
    title varchar(255),
    content text,
    primary key (id)
);