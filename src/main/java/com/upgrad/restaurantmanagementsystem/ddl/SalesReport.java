package com.upgrad.restaurantmanagementsystem.ddl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class SalesReport {

    public static void createSalesReportTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS sales_report (
                report_id SERIAL PRIMARY KEY,
                generated_by_user_id INTEGER NOT NULL,
                generated_date TIMESTAMP NOT NULL,
                total_revenue DOUBLE PRECISION NOT NULL,
                total_orders INTEGER NOT NULL
            )
        """;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("sales_report table ensured.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
