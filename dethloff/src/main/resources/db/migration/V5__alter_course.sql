create table set_of_courses(
    id VARCHAR(36) primary key,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(200)
);

alter table course
    ADD column set_of_courses_id VARCHAR(36);