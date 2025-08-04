package com.upgrad.restaurantmanagementsystem.ddl;

import com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableBooking {
    public static void createTableBookingTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS table_booking (" +
                "booking_id SERIAL PRIMARY KEY," +
                "table_number INT NOT NULL," +
                "customer_id INT NOT NULL," +
                "booking_time TIMESTAMP NOT NULL," +
                "FOREIGN KEY (customer_id) REFERENCES customer(customer_id)" +
                ")";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
