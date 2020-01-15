package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    public static final int DEFAULT_USER_ID = 1;

    private static int userId = DEFAULT_USER_ID;

    public static int authUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        SecurityUtil.userId = userId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}