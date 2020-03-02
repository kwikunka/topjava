package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import javax.persistence.PersistenceException;

@ActiveProfiles(profiles = {"sqlitedb", "jpa"})
public class UserServiceWithJdbcTest extends UserServiceTest {

    @Autowired
    private UserService service;

    @Override
    @Test(expected = PersistenceException.class)
    public void duplicateMailCreate() throws Exception {
        service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER));
    }
}
