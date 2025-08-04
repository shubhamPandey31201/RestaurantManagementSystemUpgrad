package com.upgrad.restaurantmanagementsystem.ddl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class RestaurantTable {
    public static void createRestaurantTableIfNotExists() {
        String sql = """
                CREATE TABLE IF NOT EXISTS restaurant_table (
                    table_id SERIAL PRIMARY KEY,
                    capacity INTEGER NOT NULL,
                    location_description VARCHAR(255)
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
