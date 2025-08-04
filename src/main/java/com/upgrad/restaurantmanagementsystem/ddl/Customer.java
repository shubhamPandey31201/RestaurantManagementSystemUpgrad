package com.upgrad.restaurantmanagementsystem.ddl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class Customer {

    public static void createCustomerTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS customer (" +
                "customer_id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "phone VARCHAR(15), " +
                "email VARCHAR(100), " +
                "address TEXT)";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Checked/created customer table.");
        } catch (SQLException e) {
            System.out.println("Error while creating table: " + e.getMessage());
        }
    }
}
