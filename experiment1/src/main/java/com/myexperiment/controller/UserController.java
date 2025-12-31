package com.myexperiment.controller;

import com.myexperiment.entity.User;
import com.myexperiment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // GET request to list all users
    @GetMapping
    public List<User> listUsers() {
        return userService.getAllUsers();
    }

    // POST request to add a new user (for testing)
    @PostMapping
    public String addUser(@RequestBody User user) {
        userService.register(user);
        return "User added successfully!";
    }
}