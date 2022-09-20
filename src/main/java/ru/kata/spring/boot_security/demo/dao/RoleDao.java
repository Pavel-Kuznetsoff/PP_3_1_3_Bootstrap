package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDao {

    void add(Role role);

    List<Role> getAll();

    Role getById(Long id);

    void update(Role role);

    void delete(Long id);
}
