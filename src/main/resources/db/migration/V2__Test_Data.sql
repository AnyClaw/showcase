-- Вставка преподавателей (сначала нужно создать преподавателей, чтобы привязать их к группам)
-- Преподаватели получат ID 1-6
INSERT INTO users (first_name, last_name, middle_name, password, phone_number, email, role_id, group_id)
VALUES
    ('Артем', 'Беляев', 'Викторович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234585', 'artem.belyaev@example.com', 4, NULL),
    ('Екатерина', 'Воробьева', 'Алексеевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234586', 'ekaterina.vorobyeva@example.com', 4, NULL),
    ('Денис', 'Григорьев', 'Сергеевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234587', 'denis.grigoriev@example.com', 4, NULL),
    ('Марина', 'Дмитриева', 'Игоревна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234588', 'marina.dmitrieva@example.com', 4, NULL),
    ('Роман', 'Егоров', 'Петрович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234589', 'roman.egorov@example.com', 4, NULL),
    ('Светлана', 'Жукова', 'Андреевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234590', 'svetlana.zhukova@example.com', 4, NULL);

-- Вставка групп (теперь с указанием преподавателя - user_id от 1 до 6)
INSERT INTO groups (user_id, group_name, is_group_active)
VALUES
    (1, 'УВП-211', true),  -- Преподаватель Артем Беляев
    (1, 'УВП-212', true),  -- Преподаватель Артем Беляев (ведет обе группы УВП)
    (2, 'УИС-311', true),  -- Преподаватель Екатерина Воробьева
    (2, 'УИС-312', true),  -- Преподаватель Екатерина Воробьева (ведет обе группы УИС)
    (3, 'УВВ-411', true),  -- Преподаватель Денис Григорьев
    (3, 'УВВ-412', true);  -- Преподаватель Денис Григорьев (ведет обе группы УВВ)

-- Вставка студентов (получат ID 7-41)
INSERT INTO users (first_name, last_name, middle_name, password, phone_number, email, role_id, group_id)
VALUES
    -- Группа УВП-211 (6 человек, group_id = 1)
    ('Иван', 'Петров', 'Алексеевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234567', 'ivan.petrov@example.com', 2, 1),
    ('Мария', 'Сидорова', 'Ивановна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234568', 'maria.sidorova@example.com', 2, 1),
    ('Алексей', 'Кузнецов', 'Сергеевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234569', 'alexey.kuznetsov@example.com', 2, 1),
    ('Елена', 'Соколова', 'Дмитриевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234570', 'elena.sokolova@example.com', 2, 1),
    ('Дмитрий', 'Михайлов', 'Андреевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234571', 'dmitry.mikhailov@example.com', 2, 1),
    ('Юлия', 'Новикова', 'Сергеевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234600', 'yulia.novikova@example.com', 2, 1), -- ID 13

    -- Группа УВП-212 (5 человек, group_id = 2)
    ('Анна', 'Федорова', 'Владимировна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234572', 'anna.fedorova@example.com', 2, 2), -- ID 14
    ('Сергей', 'Морозов', 'Павлович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234573', 'sergey.morozov@example.com', 2, 2), -- ID 15
    ('Татьяна', 'Волкова', 'Николаевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234574', 'tatyana.volkova@example.com', 2, 2), -- ID 16
    ('Николай', 'Алексеев', 'Евгеньевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234575', 'nikolay.alekseev@example.com', 2, 2), -- ID 17
    ('Ольга', 'Лебедева', 'Борисовна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234576', 'olga.lebedeva@example.com', 2, 2), -- ID 18

    -- Группа УИС-311 (5 человек, group_id = 3)
    ('Павел', 'Козлов', 'Станиславович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234577', 'pavel.kozlov@example.com', 2, 3), -- ID 19
    ('Наталья', 'Егорова', 'Григорьевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234578', 'natalya.egorova@example.com', 2, 3), -- ID 20
    ('Владимир', 'Семенов', 'Романович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234579', 'vladimir.semenov@example.com', 2, 3), -- ID 21
    ('Ирина', 'Павлова', 'Юрьевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234580', 'irina.pavlova@example.com', 2, 3), -- ID 22
    ('Максим', 'Андреев', 'Васильевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234581', 'maxim.andreev@example.com', 2, 3), -- ID 23

    -- Группа УИС-312 (5 человек, group_id = 4)
    ('Андрей', 'Соловьев', 'Михайлович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234601', 'andrey.soloviev@example.com', 2, 4), -- ID 24
    ('Ксения', 'Тихонова', 'Алексеевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234602', 'kseniya.tihonova@example.com', 2, 4), -- ID 25
    ('Евгений', 'Фомичев', 'Иванович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234603', 'evgeniy.fomichev@example.com', 2, 4), -- ID 26
    ('Людмила', 'Чернова', 'Петровна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234604', 'lyudmila.chernova@example.com', 2, 4), -- ID 27
    ('Григорий', 'Шевцов', 'Владимирович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234605', 'grigoriy.shevtsov@example.com', 2, 4), -- ID 28

    -- Группа УВВ-411 (5 человек, group_id = 5)
    ('Валентин', 'Щербаков', 'Константинович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234606', 'valentin.sherbakov@example.com', 2, 5), -- ID 29
    ('Альбина', 'Яковлева', 'Дмитриевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234607', 'albina.yakovleva@example.com', 2, 5), -- ID 30
    ('Виктор', 'Баранов', 'Николаевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234608', 'viktor.baranov@example.com', 2, 5), -- ID 31
    ('Евдокия', 'Ермакова', 'Семеновна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234609', 'evdokiya.ermakova@example.com', 2, 5), -- ID 32
    ('Станислав', 'Гусев', 'Анатольевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234610', 'stanislav.gusev@example.com', 2, 5), -- ID 33

    -- Группа УВВ-412 (5 человек, group_id = 6)
    ('Оксана', 'Крылова', 'Васильевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234611', 'oksana.krylova@example.com', 2, 6), -- ID 34
    ('Эдуард', 'Маслов', 'Робертович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234612', 'eduard.maslov@example.com', 2, 6), -- ID 35
    ('Зинаида', 'Осипова', 'Витальевна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234613', 'zinaida.osipova@example.com', 2, 6), -- ID 36
    ('Илья', 'Поляков', 'Сергеевич', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234614', 'ilya.polyakov@example.com', 2, 6), -- ID 37
    ('Тамара', 'Рябова', 'Ефимовна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234615', 'tamara.ryabova@example.com', 2, 6); -- ID 38

-- Вставка заказчиков (роль CLIENT) получат ID 39, 40
INSERT INTO users (first_name, last_name, middle_name, password, phone_number, email, role_id, group_id)
VALUES
    ('Кирилл', 'Никитин', 'Александрович', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234582', 'kirill.nikitin@example.com', 3, NULL),
    ('Вероника', 'Тимофеева', 'Валентиновна', '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234583', 'veronika.timofeeva@example.com', 3, NULL);

-- Вставка администратора (получит ID 41)
INSERT INTO users (first_name, last_name, middle_name, password, phone_number, email, role_id, group_id)
VALUES
    ('admin', 'admin', NULL, '$2a$10$Pb7zDts8YzPzaFXeUf8DguK1AGMuN/M/9FERvrQRTNP6c4MpscfD2', '+79161234584', 'admin@example.com', 1, NULL);

-- Вставка команд (получат ID 1-6)
INSERT INTO teams (team_name, is_team_active)
VALUES
    ('Команда "Инноваторы"', true),
    ('Команда "Техногении"', true),
    ('Команда "Киберкреатив"', true),
    ('Команда "Айтишники"', true),
    ('Команда "Цифровые пионеры"', true),
    ('Команда "Алгоритм"', true);

-- Назначение участников в команды (только из одной группы)
INSERT INTO team_members (team_id, user_id, is_leader, joined_at)
VALUES
    -- Команда "Инноваторы" (из группы УВП-211, студенты с id 7-12)
    (1, 7, true, CURRENT_TIMESTAMP),   -- Иван Петров - лидер
    (1, 8, false, CURRENT_TIMESTAMP),  -- Мария Сидорова
    (1, 9, false, CURRENT_TIMESTAMP),  -- Алексей Кузнецов
    (1, 10, false, CURRENT_TIMESTAMP), -- Елена Соколова
    (1, 11, false, CURRENT_TIMESTAMP), -- Дмитрий Михайлов
    (1, 12, false, CURRENT_TIMESTAMP), -- Юлия Новикова

    -- Команда "Техногении" (из группы УВП-212, студенты с id 13-18)
    (2, 13, true, CURRENT_TIMESTAMP),  -- Анна Федорова - лидер
    (2, 14, false, CURRENT_TIMESTAMP), -- Сергей Морозов
    (2, 15, false, CURRENT_TIMESTAMP), -- Татьяна Волкова
    (2, 16, false, CURRENT_TIMESTAMP), -- Николай Алексеев
    (2, 17, false, CURRENT_TIMESTAMP), -- Ольга Лебедева

    -- Команда "Киберкреатив" (из группы УИС-311, студенты с id 19-23)
    (3, 18, true, CURRENT_TIMESTAMP),  -- Павел Козлов - лидер
    (3, 19, false, CURRENT_TIMESTAMP), -- Наталья Егорова
    (3, 20, false, CURRENT_TIMESTAMP), -- Владимир Семенов
    (3, 21, false, CURRENT_TIMESTAMP), -- Ирина Павлова
    (3, 22, false, CURRENT_TIMESTAMP), -- Максим Андреев

    -- Команда "Айтишники" (из группы УИС-312, студенты с id 24-28)
    (4, 23, true, CURRENT_TIMESTAMP),  -- Андрей Соловьев - лидер
    (4, 24, false, CURRENT_TIMESTAMP), -- Ксения Тихонова
    (4, 25, false, CURRENT_TIMESTAMP), -- Евгений Фомичев
    (4, 26, false, CURRENT_TIMESTAMP), -- Людмила Чернова
    (4, 27, false, CURRENT_TIMESTAMP), -- Григорий Шевцов

    -- Команда "Цифровые пионеры" (из группы УВВ-411, студенты с id 29-33)
    (5, 28, true, CURRENT_TIMESTAMP),  -- Валентин Щербаков - лидер
    (5, 29, false, CURRENT_TIMESTAMP), -- Альбина Яковлева
    (5, 30, false, CURRENT_TIMESTAMP), -- Виктор Баранов
    (5, 31, false, CURRENT_TIMESTAMP), -- Евдокия Ермакова
    (5, 32, false, CURRENT_TIMESTAMP), -- Станислав Гусев

    -- Команда "Алгоритм" (из группы УВВ-412, студенты с id 34-38)
    (6, 33, true, CURRENT_TIMESTAMP),  -- Оксана Крылова - лидер
    (6, 34, false, CURRENT_TIMESTAMP), -- Эдуард Маслов
    (6, 35, false, CURRENT_TIMESTAMP), -- Зинаида Осипова
    (6, 36, false, CURRENT_TIMESTAMP), -- Илья Поляков
    (6, 37, false, CURRENT_TIMESTAMP); -- Тамара Рябова

-- Вставка проектов с правильными статусами
-- 3 проекта AVAILABLE, 1 проект ON_VERIFICATION, 1 проект IN_PROGRESS
INSERT INTO projects (user_id, status_id, title, target, barrier, existing_solution, project_type, department)
VALUES
    -- Проект 1: AVAILABLE (статус 3) - утвержден и ждет команду
    (39, 3, 'Разработка мобильного приложения для учета студентов',
     'Создать удобное мобильное приложение для отслеживания успеваемости и посещаемости студентов',
     'Недостаток опыта в мобильной разработке, ограниченные ресурсы для тестирования',
     'Существуют различные веб-системы, но нет удобного мобильного решения',
     'Прикладной', 'IT'),

    -- Проект 2: AVAILABLE (статус 3) - утвержден и ждет команду
    (40, 3, 'Автоматизация документооборота университета',
     'Внедрить систему электронного документооборота для ускорения обработки заявлений и приказов',
     'Большой объем бумажных документов, сопротивление сотрудников изменениям',
     'Используются бумажные журналы и устаревшие системы учета',
     'Инфраструктурный', 'Документооборот'),

    -- Проект 3: AVAILABLE (статус 3) - утвержден и ждет команду
    (39, 3, 'Платформа для онлайн-курсов с элементами геймификации',
     'Создать интерактивную платформу для обучения студентов с системой достижений и рейтингов',
     'Сложность реализации геймификации, высокая нагрузка на сервер',
     'Существуют стандартные LMS системы без элемента игры',
     'Стартап', 'Образование'),

    -- Проект 4: ON_VERIFICATION (статус 1) - ожидает проверки администратором
    (40, 1, 'Система распознавания студенческих билетов',
     'Разработать систему для автоматической проверки студенческих билетов с использованием компьютерного зрения',
     'Разнообразие форматов билетов, точность распознавания в плохих условиях',
     'Ручная проверка на проходной, что вызывает очереди',
     'Инновационный', 'Безопасность'),

    -- Проект 5: IN_PROGRESS (статус 5) - выполняется командой
    (39, 5, 'Чат-бот для приемной комиссии',
     'Разработать телеграм-бота для ответов на частые вопросы абитуриентов',
     'Обработка естественного языка, большое количество возможных вопросов',
     'Стандартные FAQ на сайте, которые не интерактивны',
     'Прикладной', 'IT');

-- Вставка этапов проектов (история изменений статусов)
-- Каждый этап отражает момент изменения статуса проекта
INSERT INTO project_stages (project_id, team_id, status_id, start_time, end_time)
VALUES
    -- Проект 1 (AVAILABLE): Был создан -> прошел верификацию
    (1, NULL, 1, '2024-01-10 10:00:00+00', '2024-01-11 14:30:00+00'),  -- ON_VERIFICATION (создан)
    (1, NULL, 3, '2024-01-11 14:30:00+00', NULL),                       -- AVAILABLE (одобрен админом)

    -- Проект 2 (AVAILABLE): Был создан -> прошел верификацию
    (2, NULL, 1, '2024-02-01 09:00:00+00', '2024-02-02 11:20:00+00'),  -- ON_VERIFICATION (создан)
    (2, NULL, 3, '2024-02-02 11:20:00+00', NULL),                       -- AVAILABLE (одобрен админом)

    -- Проект 3 (AVAILABLE): Был создан -> прошел верификацию
    (3, NULL, 1, '2024-02-15 13:00:00+00', '2024-02-16 09:45:00+00'),  -- ON_VERIFICATION (создан)
    (3, NULL, 3, '2024-02-16 09:45:00+00', NULL),                       -- AVAILABLE (одобрен админом)

    -- Проект 4 (ON_VERIFICATION): Только создан, еще не проверен админом
    (4, NULL, 1, '2024-03-01 10:00:00+00', NULL),                       -- ON_VERIFICATION (ждет проверки)

    -- Проект 5 (IN_PROGRESS): Прошел полный цикл до выполнения командой
    (5, NULL, 1, '2024-01-20 14:00:00+00', '2024-01-21 10:15:00+00'),  -- ON_VERIFICATION (создан)
    (5, NULL, 3, '2024-01-21 10:15:00+00', '2024-01-25 09:00:00+00'),  -- AVAILABLE (одобрен админом)
    (5, 1, 5, '2024-01-25 09:00:00+00', NULL);                         -- IN_PROGRESS (команда начала работу)