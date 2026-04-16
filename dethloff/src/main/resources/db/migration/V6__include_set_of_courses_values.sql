insert into set_of_courses(id, name, description)
values
    (
    '32d36d59-a0d0-4aa7-9dfe-223e6efc1154',
     '2024/25',
     'rok szkolny'
     ),

     (
     '49d28aee-58fc-47a5-8cf8-41c8a7b1367b',
      '2025/26',
      'rok szkolny'
      );

delete from course;

insert into course (id, name, course_level, description, teacher_id, set_of_courses_id)
values (
        'a6f85bce-e670-41ef-91cb-6d70bcbcaf84',
        'A1(1)M',
        'A1',
        'Bardzo fajna grupa, podręczniki: Konspekte2',
        '4f2e8a11-7c5b-4d3a-9e1f-6b2c8d4a5e0f',
        '32d36d59-a0d0-4aa7-9dfe-223e6efc1154'
       ),
        (
        'a561191d-cfdc-41b6-a902-48a0eeff01e8',
        'B2+',
        'B2',
        'Słaba grupa, nie weszli na C1, ale bardzo mili ;). Podręczniki Prima B2',
        '7d8e9f0a-1b2c-4d3e-af4b-5c6d7e8f9a0b',
        '49d28aee-58fc-47a5-8cf8-41c8a7b1367b'
        );

delete from course_student;

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