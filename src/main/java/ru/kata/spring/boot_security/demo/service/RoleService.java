package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    void add(Role role);

    List<Role> getAll();

    Role getById(Long id);

    void update(Role role);

    void delete(Long id);

    List<Role> convertIdToRole(List<Long> rolesId);
}
