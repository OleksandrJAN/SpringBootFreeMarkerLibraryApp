alter table book
change publicationDate publication_date DATE not null;

alter table writer
change firstName first_name varchar(127) not null;

alter table writer
change lastName last_name varchar(127) not null;