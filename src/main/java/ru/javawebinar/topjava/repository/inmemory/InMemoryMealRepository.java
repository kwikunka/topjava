package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage

        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> oldMeal = meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        if (isAvailableByUserId(meal, userId)) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return isAvailableByUserId(meal, userId) ? repository.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
    }

    private boolean isAvailableByUserId(Meal meal, int userId) {
        if (meal != null) {
            return meal.getUserId() == userId;
        }
        return false;
    }
}

