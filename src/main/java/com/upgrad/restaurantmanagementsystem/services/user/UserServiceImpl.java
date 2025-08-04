package com.upgrad.restaurantmanagementsystem.services.user;

import com.upgrad.restaurantmanagementsystem.entities.User;
import com.upgrad.restaurantmanagementsystem.repositories.user.UserDao;
import com.upgrad.restaurantmanagementsystem.repositories.user.UserDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    public UserServiceImpl() throws SQLException {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public void registerUser(User user) {
        try {
            logger.info("Registering user: {}", user.getUsername());
            userDao.save(user);
        } catch (RuntimeException e) {
            logger.error("Failed to register user: {}", user.getUsername(), e);
            throw e;
        }
    }

    @Override
    public User getUserById(int userId) {
        try {
            logger.info("Getting user by ID: {}", userId);
            return userDao.findById(userId);
        } catch (RuntimeException e) {
            logger.error("Failed to get user by ID: {}", userId, e);
            throw e;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            logger.info("Getting user by username: {}", username);
            return userDao.findByUsername(username);
        } catch (RuntimeException e) {
            logger.error("Failed to get user by username: {}", username, e);
            throw e;
        }
    }
}
