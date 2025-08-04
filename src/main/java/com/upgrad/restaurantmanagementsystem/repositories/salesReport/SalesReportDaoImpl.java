package com.upgrad.restaurantmanagementsystem.repositories.salesReport;

import com.upgrad.restaurantmanagementsystem.entities.SalesReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.upgrad.restaurantmanagementsystem.ddl.SalesReport.createSalesReportTableIfNotExists;
import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class SalesReportDaoImpl implements SalesReportDao {

    private static final Logger logger = LoggerFactory.getLogger(SalesReportDaoImpl.class);
    private final Connection connection;

    public SalesReportDaoImpl() throws SQLException {
        this.connection = getConnection();
        createSalesReportTableIfNotExists();
        logger.info("SalesReport table initialized.");
    }

    @Override
    public void save(SalesReport report) {
        String sql = "INSERT INTO sales_report (generated_by_user_id, generated_date, total_revenue, total_orders) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, report.getGeneratedByUserId());
            stmt.setTimestamp(2, Timestamp.valueOf(report.getGeneratedDate()));
            stmt.setDouble(3, report.getTotalRevenue());
            stmt.setInt(4, report.getTotalOrders());
            stmt.executeUpdate();
            logger.info("SalesReport saved: {}", report);
        } catch (SQLException e) {
            logger.error("Error saving SalesReport: {}", report, e);
            throw new RuntimeException("Error saving SalesReport", e);
        }
    }

    @Override
    public SalesReport findById(int reportId) {
        String sql = "SELECT * FROM sales_report WHERE report_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reportId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                SalesReport report = new SalesReport.Builder()
                        .reportId(rs.getInt("report_id"))
                        .generatedByUserId(rs.getInt("generated_by_user_id"))
                        .generatedDate(rs.getTimestamp("generated_date").toLocalDateTime())
                        .totalRevenue(rs.getDouble("total_revenue"))
                        .totalOrders(rs.getInt("total_orders"))
                        .build();
                logger.info("SalesReport found: {}", report);
                return report;
            } else {
                logger.warn("No SalesReport found with ID: {}", reportId);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving SalesReport with ID: {}", reportId, e);
            throw new RuntimeException("Error retrieving SalesReport", e);
        }
        return null;
    }

    @Override
    public List<SalesReport> findAll() {
        List<SalesReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM sales_report ORDER BY report_id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                SalesReport report = new SalesReport.Builder()
                        .reportId(rs.getInt("report_id"))
                        .generatedByUserId(rs.getInt("generated_by_user_id"))
                        .generatedDate(rs.getTimestamp("generated_date").toLocalDateTime())
                        .totalRevenue(rs.getDouble("total_revenue"))
                        .totalOrders(rs.getInt("total_orders"))
                        .build();
                reports.add(report);
            }
            logger.info("Total SalesReports fetched: {}", reports.size());
        } catch (SQLException e) {
            logger.error("Error fetching all SalesReports", e);
            throw new RuntimeException("Error fetching SalesReports", e);
        }
        return reports;
    }

    @Override
    public void update(SalesReport report) {
        String sql = "UPDATE sales_report SET generated_by_user_id = ?, generated_date = ?, total_revenue = ?, total_orders = ? WHERE report_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, report.getGeneratedByUserId());
            stmt.setTimestamp(2, Timestamp.valueOf(report.getGeneratedDate()));
            stmt.setDouble(3, report.getTotalRevenue());
            stmt.setInt(4, report.getTotalOrders());
            stmt.setInt(5, report.getReportId());
            int updated = stmt.executeUpdate();
            logger.info("SalesReport updated: {} | Rows affected: {}", report, updated);
        } catch (SQLException e) {
            logger.error("Error updating SalesReport: {}", report, e);
            throw new RuntimeException("Error updating SalesReport", e);
        }
    }

    @Override
    public void delete(int reportId) {
        String sql = "DELETE FROM sales_report WHERE report_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reportId);
            int deleted = stmt.executeUpdate();
            logger.info("SalesReport deleted with ID: {} | Rows affected: {}", reportId, deleted);
        } catch (SQLException e) {
            logger.error("Error deleting SalesReport with ID: {}", reportId, e);
            throw new RuntimeException("Error deleting SalesReport", e);
        }
    }
}
