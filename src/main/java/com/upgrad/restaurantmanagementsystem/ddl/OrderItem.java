package com.upgrad.restaurantmanagementsystem.ddl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class OrderItem {
    public static void createOrderItemTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS order_item (
                order_item_id SERIAL PRIMARY KEY,
                order_id INTEGER NOT NULL,
                menu_id INTEGER NOT NULL,
                quantity INTEGER NOT NULL,
                total_price DOUBLE PRECISION NOT NULL
            )
        """;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("order_item table ensured.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
