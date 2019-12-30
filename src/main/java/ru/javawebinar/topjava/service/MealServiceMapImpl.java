package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class MealServiceMapImpl implements MealService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MealServiceMapImpl.class);

    private CopyOnWriteArrayList<Meal> meals = new CopyOnWriteArrayList<>(Meal.mealsData());

    @Override
    public List<MealTo> getAll() {
        return MealsUtil.getFiltered(meals, LocalTime.MIN, LocalTime.MAX, DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public void add(Meal meal) {
        meals.add(meal);
    }

    @Override
    public void remove(int id) {
        boolean isDeleted = false;
        for (Meal meal : meals) {
            if (meal.getId() == id) {
                meals.remove(meal);
                isDeleted = true;
            }
        }
        if (!isDeleted) {
            LOGGER.warn(String.format("Запись с id=%d не была удалена", id));
        }
    }

    @Override
    public MealTo get(int id) {
        boolean isFinded = false;
        for (MealTo meal : getAll()) {
            if (meal.getId() == id) {
                isFinded = true;
                return meal;
            }
        }
        if (!isFinded) {
            LOGGER.info(String.format("Запись с id=%d не была найдена", id));
        }
        try {
            return getAll().get(id);
        } catch (IndexOutOfBoundsException ex) {
            LOGGER.error(ex.getMessage());
        }
        return null;
    }

    @Override
    public void update(int id, LocalDateTime time, String description, int calories) {
        boolean isUpdated = false;
        for (Meal meal : meals) {
            if (meal.getId() == id) {
                meal.setDateTime(time);
                meal.setDescription(description);
                meal.setCalories(calories);
                isUpdated = true;
            }
        }
        if (!isUpdated) {
            LOGGER.warn(String.format("Запись с id=%d не была обновлена", id));
        }
    }
}
