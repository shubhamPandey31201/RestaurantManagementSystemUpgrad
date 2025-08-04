package com.upgrad.restaurantmanagementsystem.ddl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class KitchenOrder {
    public static void createKitchenOrderTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS kitchen_order (
                kitchen_order_id SERIAL PRIMARY KEY,
                order_item_id INTEGER NOT NULL,
                status VARCHAR NOT NULL,
                last_updated_time TIMESTAMP NOT NULL
            )
            """;
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
