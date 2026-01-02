package com.myexperiment.mapper;

import com.myexperiment.entity.User;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserMapper {
    // Original method to get list
    List<User> findAll();

    // Original insert method (we keep this to not break old code)
    void insertUser(User user);

    // NEW: Login method
    User login(String username, String password);

    // NEW: Register method
    void register(User user);
}