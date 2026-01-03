package com.myexperiment.service;

import com.myexperiment.entity.User;
import com.myexperiment.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    // LOGIN LOGIC
    public User login(String username, String password) {
        // 1. Find the user by name
        User user = userMapper.findByUsername(username);
        
        // 2. Check if user exists AND password matches
        if (user != null && user.getPassword().equals(password)) {
            return user; // Success
        }
        return null; // Failed
    }

    // REGISTER LOGIC
    public boolean register(User user) {
        // 1. Check if username already exists
        User existing = userMapper.findByUsername(user.getUsername());
        if (existing != null) {
            return false; // Username taken
        }
        // 2. Create new user
        userMapper.createUser(user);
        return true;
    }

    // LIST ALL USERS (For Admin/Director)
    public List<User> getAllUsers() {
        return userMapper.findAllUsers();
    }

    // DELETE USER
    public void removeUser(Integer id) {
        userMapper.deleteUser(id);
    }
}