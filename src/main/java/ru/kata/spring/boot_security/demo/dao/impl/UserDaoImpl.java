package ru.kata.spring.boot_security.demo.dao.impl;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final RoleDao roleDao;

    public UserDaoImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery(
                "select u from User u", User.class).getResultList();
    }

    @Override
    public User getById(Long id) {
        Optional<User> userOptional = Optional
                .ofNullable(entityManager.find(User.class, id));
        return userOptional.orElse(new User());
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(getById(id));
    }

    @Override
    public User findByUsername(String username) {
        return entityManager
                .createQuery("select u from User u where u.username =: username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
