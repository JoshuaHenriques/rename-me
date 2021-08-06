
    create table person (
       personuuid uuid not null,
        date_of_birth varchar(45) not null,
        email varchar(45) not null,
        first_name varchar(45) not null,
        last_name varchar(45) not null,
        primary key (personuuid)
    )

    alter table person 
       add constraint UK_fwmwi44u55bo4rvwsv0cln012 unique (email)
