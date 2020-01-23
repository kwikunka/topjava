DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 1),
  ('ROLE_ADMIN', 2);

insert into meals(user_id, date_time, description, calories) VALUES
    (1, '2020-05-30 10:00:00.000', 'Завтрак', 500),
    (1, '2020-05-30 13:00:00.000', 'Обед', 1000),
    (1, '2020-05-30 20:00:00.000', 'Ужин', 500),
    (1, '2020-05-31 10:00:00.000', 'Завтрак', 1000),
    (1, '2020-05-31 13:00:00.000', 'Обед', 500),
    (1, '2020-05-31 20:00:00.000', 'Ужин', 510),
    (2, '2020-06-01 14:00:00.000', 'Админ ланч', 510),
    (2, '2020-06-01 21:00:00.000', 'Админ ужин', 1500);