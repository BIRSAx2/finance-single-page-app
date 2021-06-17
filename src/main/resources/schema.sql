create table uaa_authority
(
    name varchar(50) not null
        primary key
);

create table uaa_user
(
    id                 bigint auto_increment primary key,
    login              varchar(50)  not null,
    password_hash      varchar(60)  not null,
    first_name         varchar(50)  null,
    last_name          varchar(50)  null,
    email              varchar(191) null,
    image_url          varchar(256) null,
    activated          bit          not null,
    lang_key           varchar(10)  null,
    activation_key     varchar(20)  null,
    reset_key          varchar(20)  null,
    created_by         varchar(50)  not null,
    created_date       timestamp    null,
    reset_date         timestamp    null,
    last_modified_by   varchar(50)  null,
    last_modified_date timestamp    null,
    constraint ux_user_email
        unique (email),
    constraint ux_user_login
        unique (login)
);

create table category
(
    id              bigint auto_increment primary key,
    name            varchar(255) not null,
    color           varchar(255) null,
    show_statistics bit          null,
    user_id         bigint       null,
    foreign key (user_id) references uaa_user (id)
        on update cascade on delete cascade
);

create table uaa_user_authority
(
    user_id        bigint      not null,
    authority_name varchar(50) not null,
    primary key (user_id, authority_name),
    foreign key (authority_name) references uaa_authority (name),
    foreign key (user_id) references uaa_user (id)
);

create table wallet
(
    id          bigint auto_increment
        primary key,
    name        varchar(255) not null,
    currency    varchar(255) not null,
    count_total bit          null,
    color       varchar(255) null,
    uaa_order   int          null,
    user_id     bigint       null,
    foreign key (user_id) references uaa_user (id)
        on update cascade on delete cascade
);

create table transactions
(
    id          bigint auto_increment primary key,
    description varchar(255) null,
    amount      double       not null,
    type        varchar(255) not null,
    date        datetime(6)  not null,
    wallet_id   bigint       not null,
    category_id bigint       null,
    foreign key (category_id) references category (id)
        on update cascade on delete set null,
    foreign key (wallet_id) references wallet (id)
        on update cascade on delete cascade
);

