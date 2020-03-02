DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 1),
  ('ROLE_ADMIN', 2);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2015-05-30 10:00:00', 'Завтрак', 500, 1),
       ('2015-05-30 13:00:00', 'Обед', 1000, 1),
       ('2015-05-30 20:00:00', 'Ужин', 500, 1),
       ('2015-05-31 0:00:00', 'Еда на граничное значение', 100, 1),
       ('2015-05-31 10:00:00', 'Завтрак', 500, 1),
       ('2015-05-31 13:00:00', 'Обед', 1000, 1),
       ('2015-05-31 20:00:00', 'Ужин', 510, 1),
       ('2015-06-01 14:00:00', 'Админ ланч', 510, 2),
       ('2015-06-01 21:00:00', 'Админ ужин', 1500, 2);