create table COORDINATE (
    ID serial primary key,
    LAT float not null,
    LNG float not null,
    ALTITUDE float,
    ROUTEID bigint,
    CREATEDDATE timestamptz not null,
    HEARTRATE bigint
);

create table ROUTE (
    ID serial primary key,
    name varchar(255) not null
);

create table BLOGPOST (
    ID serial primary key,
    TITLE varchar(255) not null,
    BODY text not null,
    ROUTEID bigint
);