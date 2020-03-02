package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(profiles = {"sqlitedb", "datajpa"})
public class UserServiceWithDataJpaTest extends UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void getUserMeals() {
        List<Meal> adminMeals = service.getMeals(ADMIN_ID);
        assertMatch(adminMeals, ADMIN_MEAL1, ADMIN_MEAL2);
    }
}
