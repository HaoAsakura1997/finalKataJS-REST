package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('USER')")
public class UserController {

    private UserService userService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        String email = user.getEmail();
        model.addAttribute("user", user);
        model.addAttribute("email", email);
        model.addAttribute("roles", user.getRoles());
        return "userBoot";
    }

    @GetMapping("/userpage")
    public User oneUser(Principal principal) {
        return userService.findByUsername(principal.getName());
    }
}
