package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.DATETIME_FORMAT;

@Repository("jdbcMealRepository")
public class JdbcMealRepository implements MealRepository {

    private static final BeanPropertyRowMapper RAW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    JdbcTemplate jdbcTemplate;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    SimpleJdbcInsert simpleJdbcInsertMeal;

    @Autowired
    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                    .addValue("id", meal.getId())
                    .addValue("userId", userId)
                    .addValue("dateTime", DATETIME_FORMAT.format(meal.getDateTime()))
                    .addValue("description", meal.getDescription())
                    .addValue("calories", meal.getCalories());
        if (meal.isNew()) {
            Number id = simpleJdbcInsertMeal.executeAndReturnKey(map);
            meal.setId(id.intValue());
            return meal;
        } else {
            String sql = "UPDATE meals SET date_time = :dateTime, description = :description, calories = :calories WHERE id = :id AND user_id = :userId";
            if (namedParameterJdbcTemplate.update(sql, map) == 1) {
                return get(meal.getId(), userId);
            } else {
                return null;
            }
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        String sql = "DELETE FROM meals WHERE id = ? AND user_id = ?";
        Object[] args = new Object[]{id, userId};
        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public Meal get(int id, int userId) {
        String sql = "SELECT * FROM meals WHERE id = ? AND user_id = ?";
        Object[] args = new Object[]{id, userId};
        List<Meal> meals = jdbcTemplate.query(sql, RAW_MAPPER, args);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        String sql = "SELECT * FROM meals WHERE user_id = ? ORDER BY date_time DESC";
        Object[] args = new Object[]{userId};
        return jdbcTemplate.query(sql, RAW_MAPPER, args);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        String sql = "SELECT * FROM meals WHERE user_id = ? AND date_time BETWEEN ? and ?";
        Object[] args = new Object[]{userId, DATETIME_FORMAT.format(startDate), DATETIME_FORMAT.format(endDate)};
        return jdbcTemplate.query(sql, RAW_MAPPER, args);
    }
}
