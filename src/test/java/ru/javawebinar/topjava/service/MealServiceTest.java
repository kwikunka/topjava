package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/initDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    private static int ID = 1;

    @Autowired
    private MealService mealService;

    private Meal userMeal;

    private Meal expectedUserMeal;

    @Before
    public void setUp() {
        userMeal = mealService.get(ID, USER_ID);
        expectedUserMeal = MEALS_USER.stream()
                .filter(m -> m.getId() == ID)
                .findFirst()
                .orElse(new Meal());
    }

    @Test
    public void get() {
        assertMatch(userMeal, expectedUserMeal);
    }

    @Test(expected = NotFoundException.class)
    public void getMealNotExpected() {
        mealService.get(10, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getAnotherUserMeal() {
        mealService.get(ID, ADMIN_ID);
    }

    @Test
    public void delete() {
        mealService.delete(ID, USER_ID);
        List<Meal> userMeals = mealService.getAll(USER_ID);
        assertMatch(userMeals, getSortedMeals(MEALS_USER).stream()
                                .filter(m -> m.getId() != ID)
                                .collect(Collectors.toList()));
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherUserMeal() {
        mealService.delete(7, USER_ID);
    }

    @Test
    public void getAll() {
        List<Meal> userMeals = mealService.getAll(USER_ID);
        assertMatch(userMeals, getSortedMeals(MEALS_USER));
        List<Meal> adminMeals = mealService.getAll(ADMIN_ID);
        assertMatch(adminMeals, getSortedMeals(MEALS_ADMIN));
    }

    @Test
    public void update() {
        Meal updatedMeal = mealService.get(ID, USER_ID);
        updatedMeal.setCalories(3333);
        mealService.update(updatedMeal, USER_ID);

        assertMatch(mealService.get(ID, USER_ID), updatedMeal);
    }

    @Test(expected = NotFoundException.class)
    public void updateAnotherUserMeal() {
        Meal updatedMeal = mealService.get(ID, USER_ID);
        updatedMeal.setCalories(3333);
        mealService.update(updatedMeal, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2020, Month.MAY, 29, 20, 0), "Ужин", 510);
        Meal createdMeal = mealService.create(newMeal, USER_ID);
        newMeal.setId(createdMeal.getId());
        MEALS_USER.add(newMeal);
        assertMatch(mealService.getAll(USER_ID), getSortedMeals(MEALS_USER));
    }

    @Test(expected = DataAccessException.class)
    public void createMealWithSameDateTime() {
        mealService.create(new Meal(LocalDateTime.of(2020, Month.MAY, 29, 20, 0), "Ужин", 510), USER_ID);
        mealService.create(new Meal(LocalDateTime.of(2020, Month.MAY, 29, 20, 0), "Ужин", 510), USER_ID);
    }
}