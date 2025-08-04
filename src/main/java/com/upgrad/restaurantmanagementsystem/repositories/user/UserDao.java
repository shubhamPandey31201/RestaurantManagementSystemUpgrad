package com.upgrad.restaurantmanagementsystem.repositories.user;

import com.upgrad.restaurantmanagementsystem.entities.User;

public interface UserDao {
    void save(User user);
    User findById(int userId);
    User findByUsername(String username);
}
