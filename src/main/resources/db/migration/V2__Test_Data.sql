-- Вставка преподавателей (сначала нужно создать преподавателей, чтобы привязать их к группам)
-- Преподаватели получат ID 1-6
INSERT INTO users (first_name, last_name, middle_name, password, phone_number, email, role, group_id)
VALUES
    ('Артем', 'Беляев', 'Викторович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234585', 'artem.belyaev@example.com', 'TEACHER', NULL),
    ('Екатерина', 'Воробьева', 'Алексеевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234586', 'ekaterina.vorobyeva@example.com', 'TEACHER', NULL),
    ('Денис', 'Григорьев', 'Сергеевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234587', 'denis.grigoriev@example.com', 'TEACHER', NULL),
    ('Марина', 'Дмитриева', 'Игоревна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234588', 'marina.dmitrieva@example.com', 'TEACHER', NULL),
    ('Роман', 'Егоров', 'Петрович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234589', 'roman.egorov@example.com', 'TEACHER', NULL),
    ('Светлана', 'Жукова', 'Андреевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234590', 'svetlana.zhukova@example.com', 'TEACHER', NULL);

-- Вставка групп (теперь с указанием преподавателя - user_id от 1 до 6)
INSERT INTO groups (user_id, group_name)
VALUES
    (1, 'УВП-211'),  -- Преподаватель Артем Беляев
    (1, 'УВП-212'),  -- Преподаватель Артем Беляев (ведет обе группы УВП)
    (2, 'УИС-311'),  -- Преподаватель Екатерина Воробьева
    (2, 'УИС-312'),  -- Преподаватель Екатерина Воробьева (ведет обе группы УИС)
    (3, 'УВВ-411'),  -- Преподаватель Денис Григорьев
    (3, 'УВВ-412');  -- Преподаватель Денис Григорьев (ведет обе группы УВВ)

-- Вставка студентов (получат ID 7-41)
INSERT INTO users (first_name, last_name, middle_name, password, phone_number, email, role, group_id)
VALUES
    -- Группа УВП-211 (6 человек, group_id = 1)
    ('Иван', 'Петров', 'Алексеевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234567', 'ivan.petrov@example.com', 'STUDENT', 1),
    ('Мария', 'Сидорова', 'Ивановна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234568', 'maria.sidorova@example.com', 'STUDENT', 1),
    ('Алексей', 'Кузнецов', 'Сергеевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234569', 'alexey.kuznetsov@example.com', 'STUDENT', 1),
    ('Елена', 'Соколова', 'Дмитриевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234570', 'elena.sokolova@example.com', 'STUDENT', 1),
    ('Дмитрий', 'Михайлов', 'Андреевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234571', 'dmitry.mikhailov@example.com', 'STUDENT', 1),
    ('Юлия', 'Новикова', 'Сергеевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234600', 'yulia.novikova@example.com', 'STUDENT', 1), -- ID 13

    -- Группа УВП-212 (5 человек, group_id = 2)
    ('Анна', 'Федорова', 'Владимировна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234572', 'anna.fedorova@example.com', 'STUDENT', 2), -- ID 14
    ('Сергей', 'Морозов', 'Павлович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234573', 'sergey.morozov@example.com', 'STUDENT', 2), -- ID 15
    ('Татьяна', 'Волкова', 'Николаевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234574', 'tatyana.volkova@example.com', 'STUDENT', 2), -- ID 16
    ('Николай', 'Алексеев', 'Евгеньевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234575', 'nikolay.alekseev@example.com', 'STUDENT', 2), -- ID 17
    ('Ольга', 'Лебедева', 'Борисовна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234576', 'olga.lebedeva@example.com', 'STUDENT', 2), -- ID 18

    -- Группа УИС-311 (5 человек, group_id = 3)
    ('Павел', 'Козлов', 'Станиславович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234577', 'pavel.kozlov@example.com', 'STUDENT', 3), -- ID 19
    ('Наталья', 'Егорова', 'Григорьевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234578', 'natalya.egorova@example.com', 'STUDENT', 3), -- ID 20
    ('Владимир', 'Семенов', 'Романович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234579', 'vladimir.semenov@example.com', 'STUDENT', 3), -- ID 21
    ('Ирина', 'Павлова', 'Юрьевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234580', 'irina.pavlova@example.com', 'STUDENT', 3), -- ID 22
    ('Максим', 'Андреев', 'Васильевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234581', 'maxim.andreev@example.com', 'STUDENT', 3), -- ID 23

    -- Группа УИС-312 (5 человек, group_id = 4)
    ('Андрей', 'Соловьев', 'Михайлович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234601', 'andrey.soloviev@example.com', 'STUDENT', 4), -- ID 24
    ('Ксения', 'Тихонова', 'Алексеевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234602', 'kseniya.tihonova@example.com', 'STUDENT', 4), -- ID 25
    ('Евгений', 'Фомичев', 'Иванович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234603', 'evgeniy.fomichev@example.com', 'STUDENT', 4), -- ID 26
    ('Людмила', 'Чернова', 'Петровна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234604', 'lyudmila.chernova@example.com', 'STUDENT', 4), -- ID 27
    ('Григорий', 'Шевцов', 'Владимирович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234605', 'grigoriy.shevtsov@example.com', 'STUDENT', 4), -- ID 28

    -- Группа УВВ-411 (5 человек, group_id = 5)
    ('Валентин', 'Щербаков', 'Константинович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234606', 'valentin.sherbakov@example.com', 'STUDENT', 5), -- ID 29
    ('Альбина', 'Яковлева', 'Дмитриевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234607', 'albina.yakovleva@example.com', 'STUDENT', 5), -- ID 30
    ('Виктор', 'Баранов', 'Николаевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234608', 'viktor.baranov@example.com', 'STUDENT', 5), -- ID 31
    ('Евдокия', 'Ермакова', 'Семеновна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234609', 'evdokiya.ermakova@example.com', 'STUDENT', 5), -- ID 32
    ('Станислав', 'Гусев', 'Анатольевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234610', 'stanislav.gusev@example.com', 'STUDENT', 5), -- ID 33

    -- Группа УВВ-412 (5 человек, group_id = 6)
    ('Оксана', 'Крылова', 'Васильевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234611', 'oksana.krylova@example.com', 'STUDENT', 6), -- ID 34
    ('Эдуард', 'Маслов', 'Робертович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234612', 'eduard.maslov@example.com', 'STUDENT', 6), -- ID 35
    ('Зинаида', 'Осипова', 'Витальевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234613', 'zinaida.osipova@example.com', 'STUDENT', 6), -- ID 36
    ('Илья', 'Поляков', 'Сергеевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234614', 'ilya.polyakov@example.com', 'STUDENT', 6), -- ID 37
    ('Тамара', 'Рябова', 'Ефимовна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234615', 'tamara.ryabova@example.com', 'STUDENT', 6); -- ID 38

-- Вставка заказчиков (роль CLIENT) получат ID 39, 40
INSERT INTO users (first_name, last_name, middle_name, password, phone_number, email, role, group_id)
VALUES
    ('Кирилл', 'Никитин', 'Александрович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234582', 'kirill.nikitin@example.com', 'CLIENT', NULL),
    ('Вероника', 'Тимофеева', 'Валентиновна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234583', 'veronika.timofeeva@example.com', 'CLIENT', NULL);

-- Вставка администратора (получит ID 41)
INSERT INTO users (first_name, last_name, middle_name, password, phone_number, email, role, group_id)
VALUES
    ('admin', 'admin', NULL, '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234584', 'admin@example.com', 'ADMINISTRATOR', NULL);

-- Вставка команд (получат ID 1-6)
INSERT INTO teams (team_name)
VALUES
    ('Команда "Инноваторы"'),
    ('Команда "Техногении"'),
    ('Команда "Киберкреатив"'),
    ('Команда "Айтишники"'),
    ('Команда "Цифровые пионеры"'),
    ('Команда "Алгоритм"');

-- Назначение участников в команды (только из одной группы)
INSERT INTO team_members (team_id, user_id, is_leader)
VALUES
    -- Команда "Инноваторы" (из группы УВП-211, студенты с id 7-12)
    (1, 7, true),   -- Иван Петров - лидер
    (1, 8, false),  -- Мария Сидорова
    (1, 9, false),  -- Алексей Кузнецов
    (1, 10, false), -- Елена Соколова
    (1, 11, false), -- Дмитрий Михайлов
    (1, 12, false), -- Юлия Новикова

    -- Команда "Техногении" (из группы УВП-212, студенты с id 13-18)
    (2, 13, true),  -- Анна Федорова - лидер
    (2, 14, false), -- Сергей Морозов
    (2, 15, false), -- Татьяна Волкова
    (2, 16, false), -- Николай Алексеев
    (2, 17, false), -- Ольга Лебедева

    -- Команда "Киберкреатив" (из группы УИС-311, студенты с id 19-23)
    (3, 18, true),  -- Павел Козлов - лидер
    (3, 19, false), -- Наталья Егорова
    (3, 20, false), -- Владимир Семенов
    (3, 21, false), -- Ирина Павлова
    (3, 22, false), -- Максим Андреев

    -- Команда "Айтишники" (из группы УИС-312, студенты с id 24-28)
    (4, 23, true),  -- Андрей Соловьев - лидер
    (4, 24, false), -- Ксения Тихонова
    (4, 25, false), -- Евгений Фомичев
    (4, 26, false), -- Людмила Чернова
    (4, 27, false), -- Григорий Шевцов

    -- Команда "Цифровые пионеры" (из группы УВВ-411, студенты с id 29-33)
    (5, 28, true),  -- Валентин Щербаков - лидер
    (5, 29, false), -- Альбина Яковлева
    (5, 30, false), -- Виктор Баранов
    (5, 31, false), -- Евдокия Ермакова
    (5, 32, false), -- Станислав Гусев

    -- Команда "Алгоритм" (из группы УВВ-412, студенты с id 34-38)
    (6, 33, true),  -- Оксана Крылова - лидер
    (6, 34, false), -- Эдуард Маслов
    (6, 35, false), -- Зинаида Осипова
    (6, 36, false), -- Илья Поляков
    (6, 37, false); -- Тамара Рябова

-- Вставка проектов с правильными статусами
-- 3 проекта AVAILABLE, 1 проект ON_VERIFICATION, 1 проект IN_PROGRESS
INSERT INTO projects (user_id, project_status, title, target, barrier, existing_solution, project_type, department)
VALUES
    -- Проект 1: AVAILABLE (статус 3) - утвержден и ждет команду
    (38, 'AVAILABLE', 'Разработка мобильного приложения для учета студентов',
     'Создать удобное мобильное приложение для отслеживания успеваемости и посещаемости студентов',
     'Недостаток опыта в мобильной разработке, ограниченные ресурсы для тестирования',
     'Существуют различные веб-системы, но нет удобного мобильного решения',
     'Прикладной', 'IT'),

    -- Проект 2: AVAILABLE (статус 3) - утвержден и ждет команду
    (39, 'AVAILABLE', 'Автоматизация документооборота университета',
     'Внедрить систему электронного документооборота для ускорения обработки заявлений и приказов',
     'Большой объем бумажных документов, сопротивление сотрудников изменениям',
     'Используются бумажные журналы и устаревшие системы учета',
     'Инфраструктурный', 'Документооборот'),

    -- Проект 3: AVAILABLE (статус 3) - утвержден и ждет команду
    (38, 'AVAILABLE', 'Платформа для онлайн-курсов с элементами геймификации',
     'Создать интерактивную платформу для обучения студентов с системой достижений и рейтингов',
     'Сложность реализации геймификации, высокая нагрузка на сервер',
     'Существуют стандартные LMS системы без элемента игры',
     'Стартап', 'Образование'),

    -- Проект 4: ON_VERIFICATION (статус 1) - ожидает проверки администратором
    (39, 'ON_VERIFICATION', 'Система распознавания студенческих билетов',
     'Разработать систему для автоматической проверки студенческих билетов с использованием компьютерного зрения',
     'Разнообразие форматов билетов, точность распознавания в плохих условиях',
     'Ручная проверка на проходной, что вызывает очереди',
     'Инновационный', 'Безопасность'),

    -- Проект 5: IN_PROGRESS (статус 5) - выполняется командой
    (38, 'IN_PROGRESS', 'Чат-бот для приемной комиссии',
     'Разработать телеграм-бота для ответов на частые вопросы абитуриентов',
     'Обработка естественного языка, большое количество возможных вопросов',
     'Стандартные FAQ на сайте, которые не интерактивны',
     'Прикладной', 'IT');

-- Вставка этапов проектов (история изменений статусов)
-- Каждый этап отражает момент изменения статуса проекта
INSERT INTO project_stages (project_id, team_id, stage_status, start_time, end_time)
VALUES
    -- Проект 1 (AVAILABLE): Был создан -> прошел верификацию
    (1,NULL , 'ON_VERIFICATION', '2024-01-10 10:00:00+00', '2024-01-11 14:30:00+00'),  -- ON_VERIFICATION (создан)
    (1, NULL , 'AVAILABLE', '2024-01-11 14:30:00+00', NULL),                       -- AVAILABLE (одобрен админом)

    -- Проект 2 (AVAILABLE): Был создан -> прошел верификацию
    (2, NULL , 'ON_VERIFICATION', '2024-02-01 09:00:00+00', '2024-02-02 11:20:00+00'),  -- ON_VERIFICATION (создан)
    (2, NULL , 'AVAILABLE', '2024-02-02 11:20:00+00', NULL),                       -- AVAILABLE (одобрен админом)

    -- Проект 3 (AVAILABLE): Был создан -> прошел верификацию
    (3, NULL , 'ON_VERIFICATION', '2024-02-15 13:00:00+00', '2024-02-16 09:45:00+00'),  -- ON_VERIFICATION (создан)
    (3, NULL , 'AVAILABLE', '2024-02-16 09:45:00+00', NULL),                       -- AVAILABLE (одобрен админом)

    -- Проект 4 (ON_VERIFICATION): Только создан, еще не проверен админом
    (4, NULL, 'ON_VERIFICATION', '2024-03-01 10:00:00+00', NULL),                       -- ON_VERIFICATION (ждет проверки)

    -- Проект 5 (IN_PROGRESS): Прошел полный цикл до выполнения командой
    (5, NULL , 'ON_VERIFICATION', '2024-01-20 14:00:00+00', '2024-01-21 10:15:00+00'),  -- ON_VERIFICATION (создан)
    (5, NULL , 'AVAILABLE', '2024-01-21 10:15:00+00', '2024-01-25 09:00:00+00'),  -- AVAILABLE (одобрен админом)
    (5, 1, 'IN_PROGRESS', '2024-01-25 09:00:00+00', NULL);                         -- IN_PROGRESS (команда начала работу)