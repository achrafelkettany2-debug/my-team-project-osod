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

    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    public void register(User user) {
        userMapper.insertUser(user);
    }
}