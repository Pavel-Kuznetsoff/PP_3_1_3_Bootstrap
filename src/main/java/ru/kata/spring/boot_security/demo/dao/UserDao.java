package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    List<User> getAll();
    User getById(Long id);
    void update(User user);
    void delete(Long id);
    User findByUsername(String username);
}
