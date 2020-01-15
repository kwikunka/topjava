package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private static AtomicInteger count = new AtomicInteger(0);

    private static final List<User> USERS = Arrays.asList(new User(1, "1", "1@mail.ru", "1", Role.ROLE_USER),
                                                          new User(2, "2", "2@mail.ru", "2", Role.ROLE_USER));

    public InMemoryUserRepository() {
        USERS.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.values().removeIf(user -> user.getId() == id);
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (getByEmail(user.getEmail()) == null) {
            int id = count.incrementAndGet();
            user.setId(id);
            return repository.put(id, user);
        }
        log.error("user with email={} already exist", user.getEmail());
        return null;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        Optional<User> user = repository.values().stream()
                            .filter(u -> u.getId() == id)
                            .findFirst();
        return user.orElse(null);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");

        return repository.values().stream()
                       .sorted(Comparator.comparing(AbstractNamedEntity::getName))
                       .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        Optional<User> user = repository.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findAny();
        return user.orElse(null);
    }
}
