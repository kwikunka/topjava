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
(1, '1590850000000', 'Завтрак', 500),
(1, '1590860000000', 'Обед', 1000),
(1, '1590870000000', 'Ужин', 500),
(1, '1590880000000', 'Завтрак', 1000),
(1, '1590890000000', 'Обед', 500),
(1, '1590900000000', 'Ужин', 510),
(2, '1590910000000', 'Админ ланч', 510),
(2, '1590920000000', 'Админ ужин', 1500);