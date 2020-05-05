create table TRAVELER (
    ID int not null AUTO_INCREMENT,
    NAME varchar(255),
    EMAIL varchar(255),
    PASSWORDHASH varchar(255),
    PASSWORDSALT varchar(255),
    PRIMARY KEY(ID)
)