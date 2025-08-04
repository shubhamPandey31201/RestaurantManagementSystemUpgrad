package com.upgrad.restaurantmanagementsystem.ddl;

import com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    public static void createUserTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id SERIAL PRIMARY KEY," +
                "username VARCHAR(255) NOT NULL UNIQUE," +
                "password VARCHAR(255) NOT NULL," +
                "role VARCHAR(50) NOT NULL" +
                ")";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
