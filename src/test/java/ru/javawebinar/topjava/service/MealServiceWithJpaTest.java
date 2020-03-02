package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"sqlitedb", "jpa"})
public class MealServiceWithJpaTest extends MealServiceTest {
}
