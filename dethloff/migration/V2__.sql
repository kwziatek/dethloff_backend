CREATE TABLE course
(
    id           VARCHAR(255) NOT NULL,
    course_level VARCHAR(255),
    description  VARCHAR(255),
    teacher_id   VARCHAR(255),
    name         VARCHAR(255) NOT NULL,
    CONSTRAINT course_pkey PRIMARY KEY (id)
);

CREATE TABLE course_student
(
    course_id  VARCHAR(255) NOT NULL,
    student_id VARCHAR(255) NOT NULL,
    CONSTRAINT course_student_pkey PRIMARY KEY (course_id, student_id)
);

CREATE TABLE student
(
    id             VARCHAR(255) NOT NULL,
    name           VARCHAR(255),
    surname        VARCHAR(255),
    is_active      BOOLEAN,
    pesel          VARCHAR(11),
    place_of_birth VARCHAR(255),
    birth_date     date,
    gender         VARCHAR(255),
    CONSTRAINT student_pkey PRIMARY KEY (id)
);

CREATE TABLE teacher
(
    id             VARCHAR(255) NOT NULL,
    name           VARCHAR(255) NOT NULL,
    surname        VARCHAR(255) NOT NULL,
    pesel          VARCHAR(11),
    place_of_birth VARCHAR(255),
    birth_date     date,
    is_active      BOOLEAN,
    gender         VARCHAR(255),
    CONSTRAINT teacher_pkey PRIMARY KEY (id)
);

ALTER TABLE course_student
    ADD CONSTRAINT fklmj50qx9k98b7li5li74nnylb FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE NO ACTION;

ALTER TABLE course
    ADD CONSTRAINT fksybhlxoejr4j3teomm5u2bx1n FOREIGN KEY (teacher_id) REFERENCES teacher (id) ON DELETE NO ACTION;