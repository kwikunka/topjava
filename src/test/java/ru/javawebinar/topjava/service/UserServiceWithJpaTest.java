package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"sqlitedb", "jdbc"})
public class UserServiceWithJpaTest extends UserServiceTest {
}
