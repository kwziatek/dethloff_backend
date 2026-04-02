create table if not exists teacher (
    id VARCHAR(36) primary key,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    pesel VARCHAR(11),
    place_of_birth VARCHAR(50),
    birth_date DATE,
    is_active boolean,
    gender VARCHAR(10)
);

insert into teacher (id, name, surname, pesel, place_of_birth, birth_date, is_active, gender)
values ('4f2e8a11-7c5b-4d3a-9e1f-6b2c8d4a5e0f',
        'Tomasz',
        'Wziątek',
        '94031222911',
        'Lublin',
        '1994-03-12',
        'false',
        'MALE'),

    ('b9c1d2e3-f4a5-4b6c-8d7e-9f0a1b2c3d4e',
    'Katarzyna',
    'Szczygieł',
    '84072746644',
    'Wrocław',
    '1984-07-27',
    'false',
    'FEMALE'),

    ('a1b2c3d4-e5f6-4a7b-8c9d-0e1f2a3b4c5d',
     'Kamil',
     'Ziemba',
     '76093016611',
     'Nałęczów',
     '1976-09-30',
     'false',
     'MALE'),

   ('7d8e9f0a-1b2c-4d3e-af4b-5c6d7e8f9a0b',
    'Anna',
    'Sochal',
    '87051236622',
    'Wrocław',
    '1987-05-12',
    'false',
    'FEMALE');

create table if not exists student (
                            id VARCHAR(36) primary key,
                            name VARCHAR(50) NOT NULL,
                            surname VARCHAR(50) NOT NULL,
                            pesel VARCHAR(11),
                            place_of_birth VARCHAR(50),
                            birth_date DATE,
                            is_active boolean,
                            gender VARCHAR(10)
);

insert into student (id, name, surname, pesel, place_of_birth, birth_date, is_active, gender)
values (
        'c6f5ce13-92ab-4b31-91c8-df69703e22ec',
        'Krzysztof',
        'Brzytwa',
        '09301075533',
        'Poznań',
        '2009-10-10',
        'false',
        'MALE'
       ),

       (
        '60cc11ed-22ef-48e9-af8a-fa72e397cdbc',
        'Szymon',
        'Rudzik',
        '80050742376',
        'Wałbrzych',
        '1980-05-07',
        'false',
        'MALE'
       ),

       (
        '72863fbe-d04f-49f0-81de-5d0da0c98de3',
        'Joanna',
        'Preczak',
        '07281216241',
        'Lublin',
        '2007-08-12',
        'false',
        'FEMALE'
       );

create table if not exists course (
    id VARCHAR(36) primary key,
    name VARCHAR(20) NOT NULL,
    course_level VARCHAR(2),
    description VARCHAR(200),
    teacher_id VARCHAR(36)
);

insert into course (id, name, course_level, description, teacher_id)
values (
        'a6f85bce-e670-41ef-91cb-6d70bcbcaf84',
        'A1(1)M',
        'A1',
        'Bardzo fajna grupa, podręczniki: Konspekte2',
        '4f2e8a11-7c5b-4d3a-9e1f-6b2c8d4a5e0f'
       ),
    (
    'a561191d-cfdc-41b6-a902-48a0eeff01e8',
    'B2+',
    'B2',
    'Słaba grupa, nie weszli na C1, ale bardzo mili ;). Podręczniki Prima B2',
    '7d8e9f0a-1b2c-4d3e-af4b-5c6d7e8f9a0b'
    );

create table if not exists course_student (
    id VARCHAR(36) primary key,
    course_id VARCHAR(36) NOT NULL,
    student_id VARCHAR(36) NOT NULL
);

insert into course_student
values (
        '78e30071-57a7-4531-9426-57bbcde079a2',
        'a6f85bce-e670-41ef-91cb-6d70bcbcaf84',
        '72863fbe-d04f-49f0-81de-5d0da0c98de3'
       ),

    (
    '0f151f2e-de72-4e8e-bd38-9ab0969d7f92',
    'a6f85bce-e670-41ef-91cb-6d70bcbcaf84',
    '60cc11ed-22ef-48e9-af8a-fa72e397cdbc'
    ),

    (
     'b2d14bd9-99f8-48e6-bdc1-84be4c7d9968',
     'a561191d-cfdc-41b6-a902-48a0eeff01e8',
     '72863fbe-d04f-49f0-81de-5d0da0c98de3'
    );



