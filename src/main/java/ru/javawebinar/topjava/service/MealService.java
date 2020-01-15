package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Collection<MealTo> getAll(int userId) {
        return MealsUtil.getTos(repository.getAll(userId), userId, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public void update(Meal meal, int userId) {
        if (meal.getUserId() == userId) {
            checkNotFoundWithId(repository.save(meal), meal.getId());
        } else {
            throw new NotFoundException("Not found entity with id=" + meal.getId());
        }
    }

    public List<MealTo> filter(int userId, LocalDate dateAfter, LocalDate dateBefore, LocalTime timeAfter, LocalTime timeBefore) {
        return MealsUtil.getFilteredTos(repository.getAll(userId), userId, MealsUtil.DEFAULT_CALORIES_PER_DAY, dateAfter, dateBefore, timeAfter,timeBefore);
    }
}