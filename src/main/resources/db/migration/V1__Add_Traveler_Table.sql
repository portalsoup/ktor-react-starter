create table TRAVELER (
    ID serial primary key,
    EMAIL varchar(255) not null,
    PASSWORDHASH varchar(255) not null,
    PASSWORDSALT varchar(255) not null
)