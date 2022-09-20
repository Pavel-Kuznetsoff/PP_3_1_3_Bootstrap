package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("admin/users")
public class UserController {

    private UserService userService;
    private RoleService roleService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String showAll(Model model) {
        System.out.println(userService.getAll());
        model.addAttribute("users", userService.getAll());
        return "users/all_users";
    }

    @GetMapping("/{id}")
    public String showOne(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "users/user_info";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles", roleService.getAll());
        return "users/new_user";
    }

    @PostMapping
    public String create(
            @ModelAttribute("user") User user,
            @RequestParam(value = "rolesId", required = false) List<Long> rolesId
    ) {

        user.setRoles(roleService.convertIdToRole(rolesId));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("allRoles", roleService.getAll());
        return "users/edit_user";
    }

    @PatchMapping("/{id}")
    public String update(
            @ModelAttribute("user") User user,
            @RequestParam(value = "rolesId", required = false) List<Long> rolesId
    ) {
        user.setRoles(roleService.convertIdToRole(rolesId));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
