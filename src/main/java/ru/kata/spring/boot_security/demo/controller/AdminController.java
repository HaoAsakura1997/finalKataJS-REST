package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;




@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private UserService userService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String adminPage() {
        return "admin";
    }


    @GetMapping("/userList")
    public String getUserList(Model model, Authentication authentication, @ModelAttribute User user) {
        model.addAttribute("user", userService.findByUsername(authentication.getName()));
        model.addAttribute("listRoles", userService.listRoles());
        model.addAttribute("users", userService.findAll());
        return "manage";
    }

    @GetMapping("/edit/{id}")
    public String userEditForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", userService.listRoles());
        return "editUser";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute User user, Model model) {
        model.addAttribute("roles", userService.listRoles());
        userService.saveUser(user);
        return "redirect:/admin/userList";
    }


    @GetMapping("/add")
    public String addUserForm(Model model, User user, Authentication authentication) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", userService.listRoles());
        model.addAttribute("auth",userService.findByUsername(authentication.getName()));
        return "addBoot";
    }


    @PostMapping("/add")
    public String addUser (@ModelAttribute User user) {
        userService.saveUser(user);

        return "redirect:/admin/userList";
    }

    @GetMapping("/delete")
    public String deleteUser(User user) {
        userService.deleteById(user.getId());
        return "redirect:/admin/userList";
    }


}
