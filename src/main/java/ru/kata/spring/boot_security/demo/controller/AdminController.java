package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private UserService userService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }




    @GetMapping
    public String getUserList(Model model, Authentication authentication, @ModelAttribute User user) {
        model.addAttribute("user", userService.findByUsername(authentication.getName()));
        model.addAttribute("amountOfRoles", userService.listRoles());
        model.addAttribute("users", userService.findAll());
        return "manage";
    }

    @GetMapping("/adminpage")
    public ResponseEntity<List<User>> userList() {
        final List<User> users = userService.findAll();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/adminpage")
    public List<User> addUser(@RequestBody User user) {
        userService.saveUser(user);
        return userService.findAll();
    }

    @PutMapping("/adminpage")
    public List<User> update(@RequestBody User user) {
        userService.saveUser(user);
        return userService.findAll();
    }

    @DeleteMapping("/adminpage/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
