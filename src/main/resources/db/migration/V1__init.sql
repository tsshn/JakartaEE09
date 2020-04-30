create table if not exists library (
    id INT auto_increment primary key,
    title VARCHAR(50) not null,
    author VARCHAR(50) not null,
    isbn VARCHAR(50) not null
);

create table if not exists users (
    id int primary key auto_increment,
    login varchar(50) not null,
    password varchar(50) not null,
    unique uniq_login (login)
);

create table if not exists permissions (
    id int primary key auto_increment,
    permission varchar(50) not null,
    unique uniq_permission (permission)
);

create table if not exists user_to_permissions (
    user_id int not null,
    permission_id int not null,
    constraint fk_user_to_permission_user foreign key (user_id) references users (id),
    constraint fk_user_to_permission_permission foreign key (permission_id) references permissions (id)
);

create table user_to_liked_book (
    book_id int not null,
    user_id int not null,
    constraint fk_user_to_liked_book_user foreign key (user_id) references users (id),
    constraint fk_user_to_liked_book_book foreign key (book_id) references library (id)
);

insert into
    library (TITLE, AUTHOR, ISBN)
VALUES
    ('Code Name Hélène', 'Ariel Lawhon', '0385544685'),
    ('Pretty Things', 'Janelle Brown', '0525479120'),
    ('Three Hours in Paris', 'Cara Black', '1641290412'),
    ('American Dirt', 'Jeanine Cummins', '0245790811');

insert into
    users (login, password)
values
    ('librarian', '123581321'),
    ('reader', '1.6180339');

insert into
    permissions (permission)
values
    ('VIEW_ADMIN'),
    ('VIEW_CATALOG');

insert into
    user_to_permissions (user_id, permission_id)
values
(
        (
            select
                id
            from
                users
            where
                login = 'librarian'
        ),
        (
            select
                id
            from
                permissions
            where
                permission = 'VIEW_ADMIN'
        )
    ),
    (
        (
            select
                id
            from
                users
            where
                login = 'reader'
        ),
        (
            select
                id
            from
                permissions
            where
                permission = 'VIEW_CATALOG'
        )
    );