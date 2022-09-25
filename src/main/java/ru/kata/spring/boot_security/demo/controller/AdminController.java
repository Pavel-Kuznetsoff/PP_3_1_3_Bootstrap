package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String usersTable(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAll());
        model.addAttribute("allRoles", roleService.getAll());
        return "admin";
    }

    @GetMapping("/users/{id}")
    public String showOne(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "users/user_info";
    }

    @GetMapping("/users/new")
    public String newUser(@ModelAttribute("newUser") User newUser, Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("allRoles", roleService.getAll());
        model.addAttribute("user", user);
        return "users/new_user";
    }

    @PostMapping
    public String create(
            @ModelAttribute("newUser") User user,
            @RequestParam(value = "rolesId", required = false) List<Long> rolesId
    ) {

        user.setRoles(roleService.convertIdToRole(rolesId));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);
        return "redirect:/admin";
    }
// TODO : удалить лишние контроллеры

//    @GetMapping("users/{id}/edit")
//    public String edit(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("user", userService.getById(id));
//        model.addAttribute("allRoles", roleService.getAll());
//        return "users/edit_user";
//    }

    @PatchMapping("users/{id}/edit")
    public String update(
            @ModelAttribute("user") User user,
            @RequestParam(value = "rolesId", required = false) List<Long> rolesId
    ) {
        user.setRoles(roleService.convertIdToRole(rolesId));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
