package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceMapImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class MealServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(MealServlet.class);
    private static final String ADD = "add";
    private static final String DELETE = "delete";
    private static final String GET = "get";
    private static final String UPDATE = "update";

    private MealService mealService = new MealServiceMapImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Optional<String> action = Optional.ofNullable(request.getParameter("action"));
        Integer id = getId(request);

        switch (action.orElse("get")) {
            case DELETE:
                if (id == null) {
                    LOGGER.warn("Не передан id записи для удаления");
                } else {
                    mealService.remove(id);
                }
                response.sendRedirect("meals");
                break;
            case UPDATE:
                if (id == null || mealService.get(id) == null) {
                    LOGGER.warn(String.format("Не найдена запись по id %s", id));
                } else {
                    request.setAttribute("meal", mealService.get(id));
                    request.getRequestDispatcher("/updateMeal.jsp").forward(request, response);
                }
                break;
            case GET:
            default:
                if (id == null) {
                    request.setAttribute("meals", mealService.getAll());
                } else {
                    Optional<MealTo> mealTo = Optional.ofNullable(mealService.get(id.intValue()));
                    request.setAttribute("meals", mealTo.isPresent() ? Arrays.asList(mealTo.get()) : Collections.emptyList());
                }

                request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        Integer id = getId(request);

        if (ADD.equals(action)) {
            LocalDateTime localDateTime;

            try {
                localDateTime = LocalDateTime.parse(request.getParameter("datetime"));
            } catch (DateTimeParseException ex) {
                LOGGER.warn("Время не было передано, присвоено текущее значение.", ex);
                localDateTime = LocalDateTime.now();
            }

            String description = request.getParameter("description");

            int calories = 0;
            try {
                calories = Integer.parseInt(request.getParameter("calories"));
            } catch (NumberFormatException ex) {
                LOGGER.error("Передано некорректное значение калорий", ex);
            }
            if (id == null) {
                Meal meal = new Meal(localDateTime, description, calories);
                mealService.add(meal);
            } else {
                mealService.update(id, localDateTime, description, calories);
            }
        }

        doGet(request, response);
    }

    private Integer getId(HttpServletRequest request) {
        Integer id = null;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage());
        }
        return id;
    }
}