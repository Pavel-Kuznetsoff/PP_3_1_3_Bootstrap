package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class MainController {

    private UserService service;

    public MainController(UserService service) {
        this.service = service;
    }

    @GetMapping("/admin")
    public String showAdmin(Principal principal, Model model) {
        User user = service.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/admin";
    }

    @GetMapping("/user")
    public String showUser(Principal principal, Model model) {
        User user = service.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/user";
    }

    @GetMapping("/403")
    public String error403() {
        return "403";
    }
}
