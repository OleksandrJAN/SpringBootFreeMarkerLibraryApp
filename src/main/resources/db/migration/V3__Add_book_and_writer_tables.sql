create table book (
    id bigint not null auto_increment,
    bookName varchar(127) not null,
    annotation varchar(2048) not null,
    publicationDate DATE not null,
    filename varchar(255) not null,
    writer_id bigint,
    primary key (id)
);

create table book_genre (
    book_id bigint not null,
    genres varchar(255)
);

create table writer (
    id bigint not null auto_increment,
    firstName varchar(127) not null,
    lastName varchar(127) not null,
    primary key (id)
);

alter table book
    add constraint book_writer_fk
    foreign key (writer_id) references writer (id);

alter table book_genre
    add constraint book_book_genre_fk
    foreign key (book_id) references book (id);