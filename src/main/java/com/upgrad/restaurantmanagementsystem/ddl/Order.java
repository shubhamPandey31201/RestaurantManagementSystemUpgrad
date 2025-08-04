package com.upgrad.restaurantmanagementsystem.ddl;

import java.sql.Connection;
import java.sql.Statement;

import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class Order {

    public static void createOrderTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS orders (
                order_id SERIAL PRIMARY KEY,
                customer_id INTEGER,
                total_amount DOUBLE PRECISION
            );
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Table 'orders' checked/created.");

        } catch (Exception e) {
            System.err.println("Failed to create table: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
