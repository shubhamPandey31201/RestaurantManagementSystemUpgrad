package com.upgrad.restaurantmanagementsystem.ddl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class MenuItem {
    public static void createMenuItemTableIfNotExists() {
        String sql = """
                CREATE TABLE IF NOT EXISTS menu_item (
                    menu_id SERIAL PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    description TEXT,
                    price DOUBLE PRECISION NOT NULL
                );
                """;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
