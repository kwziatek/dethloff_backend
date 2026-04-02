alter table student
add column address_data_id VARCHAR(36);

create table if not exists student_address_data (
                                       id VARCHAR(36) primary key,
                                       city VARCHAR(50),
                                       street VARCHAR(50),
                                       flat_number VARCHAR(50),
                                       postal_code VARCHAR(50),
                                       phone_number VARCHAR(50),
                                       email VARCHAR(50)

);