drop table message;

create table review (
    id bigint not null auto_increment,
    text varchar(2048) not null,
    assessment varchar(128) not null,
    book_id bigint,
    user_id bigint,
    primary key (id)
);

alter table review
    add constraint review_user_fk
    foreign key (user_id) references usr (id);

alter table review
    add constraint review_book_fk
    foreign key (book_id) references book (id);