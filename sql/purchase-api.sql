
    drop table cart if exists;

    drop table item if exists;

    create table cart (
        id varchar(255) not null,
        version bigint not null,
        added_at timestamp,
        card varchar(255) not null,
        customer varchar(255) not null,
        edited_at timestamp,
        email varchar(255) not null,
        expires_on timestamp,
        primary key (id)
    );

    create table item (
        id varchar(255) not null,
        version bigint not null,
        added_at timestamp,
        amount integer not null,
        cart_id varchar(255) not null,
        edited_at timestamp,
        product_id varchar(255) not null,
        primary key (id)
    );

    alter table item 
        add constraint FK_7er5q7h130hvjtolhd1etvyfb 
        foreign key (cart_id) 
        references cart;
