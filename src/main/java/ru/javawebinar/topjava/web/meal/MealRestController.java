package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Controller
public class MealRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Collection<MealTo> getAll() {
        LOGGER.info("getAll meals");
        return service.getAll(SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        LOGGER.info("get meal id={}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        LOGGER.info("create meal={}", meal);
        return service.create(meal);
    }

    public void delete(int id) {
        LOGGER.info("delete meal id={}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal) {
        LOGGER.info("update meal id={}", meal.getId());
        service.update(meal, SecurityUtil.authUserId());
    }

    public List<MealTo> filter(int userId, LocalDate dateAfter, LocalDate dateBefore, LocalTime timeAfter, LocalTime timeBefore) {
        LOGGER.info("filter daterAfter={}, dateBefore={}, timeAfter={}, timeBefore={}\", dateAfter, dateBefore, timeAfter, timeBefore");
        return service.filter(userId, dateAfter, dateBefore, timeAfter, timeBefore);
    }
}