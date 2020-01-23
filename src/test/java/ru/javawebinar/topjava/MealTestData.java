package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.rmi.MarshalException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static List<Meal> MEALS_USER = new ArrayList<>(Arrays.asList(
            new Meal(1, LocalDateTime.of(2020, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(2, LocalDateTime.of(2020, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(3, LocalDateTime.of(2020, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(4, LocalDateTime.of(2020, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(5, LocalDateTime.of(2020, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(6, LocalDateTime.of(2020, Month.MAY, 31, 20, 0), "Ужин", 510)
    ));

    public static List<Meal> MEALS_ADMIN = Arrays.asList(
            new Meal(7, LocalDateTime.of(2020, Month.JUNE, 01, 14, 0), "Админ ланч", 510),
            new Meal(8, LocalDateTime.of(2020, Month.JUNE, 01, 21, 0), "Админ ужин", 1500)
    );

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "userId");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("userId").isEqualTo(expected);
    }

    public static List<Meal> getSortedMeals(List<Meal> meals) {
        meals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        return meals;
    }
}
