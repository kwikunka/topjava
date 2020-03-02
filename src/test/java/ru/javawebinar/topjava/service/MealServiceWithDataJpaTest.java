package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"sqlitedb", "datajpa"})
public class MealServiceWithDataJpaTest extends MealServiceTest {
}
