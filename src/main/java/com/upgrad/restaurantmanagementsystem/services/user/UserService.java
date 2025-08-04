package com.upgrad.restaurantmanagementsystem.services.user;

import com.upgrad.restaurantmanagementsystem.entities.User;

public interface UserService {
    void registerUser(User user);
    User getUserById(int userId);
    User getUserByUsername(String username);
}
