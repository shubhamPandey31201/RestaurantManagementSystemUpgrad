package com.upgrad.restaurantmanagementsystem.repositories.user;

import com.upgrad.restaurantmanagementsystem.entities.Role;
import com.upgrad.restaurantmanagementsystem.entities.RoleName;
import com.upgrad.restaurantmanagementsystem.entities.User;
import com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import static com.upgrad.restaurantmanagementsystem.ddl.User.createUserTableIfNotExists;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private final Connection connection;

    public UserDaoImpl() throws SQLException {
        this.connection = DatabaseUtil.getConnection();
        createUserTableIfNotExists();
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().getRoleName().name());
            ps.executeUpdate();
            logger.info("User saved successfully: {}", user.getUsername());
        } catch (SQLException e) {
            logger.error("Error saving user: {}", user.getUsername(), e);
            throw new RuntimeException("Failed to save user", e);
        }
    }

    @Override
    public User findById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = mapResultSetToUser(rs);
                logger.info("User found by ID {}: {}", userId, user.getUsername());
                return user;
            } else {
                logger.warn("No user found with ID: {}", userId);
            }
        } catch (SQLException e) {
            logger.error("Error finding user by ID: {}", userId, e);
            throw new RuntimeException("Failed to find user by ID", e);
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = mapResultSetToUser(rs);
                logger.info("User found by username: {}", username);
                return user;
            } else {
                logger.warn("No user found with username: {}", username);
            }
        } catch (SQLException e) {
            logger.error("Error finding user by username: {}", username, e);
            throw new RuntimeException("Failed to find user by username", e);
        }
        return null;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        RoleName roleName = RoleName.valueOf(rs.getString("role").toUpperCase());
        Role role = new Role.Builder()
                .roleId(0)
                .roleName(roleName)
                .build();

        return new User.Builder()
                .userId(rs.getInt("user_id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .role(role)
                .build();
    }
}
