package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {
    void add(Meal meal);
    List<MealTo> getAll();
    void remove(int id);
    MealTo get(int id);
    void update(int id, LocalDateTime time, String Description, int calories);

}
