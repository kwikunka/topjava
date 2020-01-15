package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll();

            InMemoryUserRepository repository = appCtx.getBean(InMemoryUserRepository.class);
            System.out.println(repository.getAll());
            System.out.println(repository.delete(1));
            System.out.println(repository.getAll());
            System.out.println(repository.save(new User(4, "4", "4@mail.ru", "4", Role.ROLE_USER)));
            System.out.println(repository.getAll());
            System.out.println(repository.get(4));
            System.out.println(repository.get(5));
            System.out.println(repository.delete(5));
        }
    }
}
