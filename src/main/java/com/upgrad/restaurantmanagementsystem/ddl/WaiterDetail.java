package com.upgrad.restaurantmanagementsystem.ddl;

import com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class WaiterDetail {

    public static void createWaiterDetailTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS waiter_details (" +
                "user_id INTEGER PRIMARY KEY," +
                "shift VARCHAR(255)" +
                ")";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
