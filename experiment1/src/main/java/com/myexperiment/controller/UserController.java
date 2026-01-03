package com.myexperiment.controller;

import com.myexperiment.entity.User;
import com.myexperiment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.HashMap; // Imported HashMap
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // LOGIN (Fixed to return 'status' AND 'user')
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User loginRequest) {
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success"); // Login.vue needs this!
            response.put("user", user);
            return response;
        } else {
            return Collections.singletonMap("status", "error");
        }
    }

    // REGISTER
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        if (user.getRole() == null) user.setRole("student");
        
        boolean success = userService.register(user);
        if (success) {
            return Collections.singletonMap("status", "success");
        } else {
            return Collections.singletonMap("status", "error");
        }
    }

    // LIST ALL USERS
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public Map<String, String> deleteUser(@PathVariable Integer id) {
        userService.removeUser(id);
        return Collections.singletonMap("status", "success");
    }
}