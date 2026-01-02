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

    // Get all users
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    // Login Logic
    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    // Register Logic
    public void register(User user) {
        // If role is empty, default to student
        if(user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("student");
        }
        userMapper.register(user);
    }
}