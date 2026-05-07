create table client
    (
        id VARCHAR(36) primary key,
        username VARCHAR(30) NOT NULL,
        password VARCHAR(60) NOT NULL,
        role VARCHAR(10) NOT NULL
    );

insert into client (id, username, password, role)
values
(
 '79b41fc5-9ba9-4d96-96a9-01f6f31f2de0', 'dev', '0', 'admin'
);

