package com.myexperiment.controller;

import com.myexperiment.entity.User;
import com.myexperiment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Existing: Get all users
    @GetMapping
    public List<User> listUsers() {
        return userService.getAllUsers();
    }

    // Existing: Add user (Experiment 1 style - keeps old code working)
    @PostMapping
    public String addUser(@RequestBody User user) {
        userService.register(user);
        return "User added successfully";
    }

    // NEW: Login API
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User loginUser) {
        User user = userService.login(loginUser.getUsername(), loginUser.getPassword());
        
        if (user != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("role", user.getRole());
            response.put("username", user.getUsername());
            response.put("id", user.getId());
            return response;
        } else {
            return Collections.singletonMap("status", "fail");
        }
    }

    // NEW: Register API (The modern JSON version)
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        userService.register(user);
        return Collections.singletonMap("status", "success");
    }
}