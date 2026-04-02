drop table if exists student_address_data;

alter table student
    ADD column city VARCHAR(50),
    ADD column street VARCHAR(100),
    ADD column flat_number VARCHAR(20),
    ADD column postal_code VARCHAR(10),
    ADD column phone_number VARCHAR(11),
    ADD column email VARCHAR(50),
    ADD column guardian_name VARCHAR(30),
    ADD column guardian_surname VARCHAR(30),
    ADD column guardian_city VARCHAR(50),
    ADD column guardian_street VARCHAR(100),
    ADD column guardian_flat_number VARCHAR(20),
    ADD column guardian_postal_code VARCHAR(10),
    ADD column guardian_phone_number VARCHAR(11),
    ADD column guardian_email VARCHAR(50),
    ADD column company_name VARCHAR(100),
    add column NIP VARCHAR(14),
    ADD column company_city VARCHAR(50),
    ADD column company_street VARCHAR(100),
    ADD column company_flat_number VARCHAR(20),
    ADD column company_postal_code VARCHAR(10),
    ADD column company_phone_number VARCHAR(11),
    ADD column company_email VARCHAR(50),
    ADD column marketing_sources VARCHAR(100);




