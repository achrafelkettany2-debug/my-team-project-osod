package com.myexperiment.mapper;

import com.myexperiment.entity.User;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserMapper {
    List<User> findAll();
    void insertUser(User user);
}