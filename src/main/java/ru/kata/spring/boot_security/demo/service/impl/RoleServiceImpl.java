package ru.kata.spring.boot_security.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private RoleDao dao;

    @Autowired
    public RoleServiceImpl(RoleDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public void add(Role role) {
        dao.add(role);
    }

    @Override
    public List<Role> getAll() {
        return dao.getAll();
    }

    @Override
    public Role getById(Long id) {
        return dao.getById(id);
    }

    @Override
    @Transactional
    public void update(Role role) {
        dao.update(role);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    public List<Role> convertIdToRole(List<Long> rolesId) {
        return rolesId.stream()
                .map(aLong -> getById(aLong))
                .collect(Collectors.toList());
    }
}
