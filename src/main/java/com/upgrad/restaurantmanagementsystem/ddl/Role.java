package com.upgrad.restaurantmanagementsystem.ddl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class Role {
    public static void createRoleTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS role (" +
                "role_id SERIAL PRIMARY KEY, " +
                "role_name VARCHAR(50) UNIQUE NOT NULL)";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating role table", e);
        }
    }
}
