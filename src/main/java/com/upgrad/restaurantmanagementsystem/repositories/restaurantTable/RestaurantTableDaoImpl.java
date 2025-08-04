package com.upgrad.restaurantmanagementsystem.repositories.restaurantTable;

import com.upgrad.restaurantmanagementsystem.entities.RestaurantTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.upgrad.restaurantmanagementsystem.ddl.RestaurantTable.createRestaurantTableIfNotExists;
import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class RestaurantTableDaoImpl implements RestaurantTableDao {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantTableDaoImpl.class);
    private final Connection connection;

    public RestaurantTableDaoImpl() throws SQLException {
        this.connection = getConnection();
        createRestaurantTableIfNotExists();
    }

    @Override
    public void save(RestaurantTable table) {
        String sql = "INSERT INTO restaurant_table (capacity, location_description) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, table.getCapacity());
            stmt.setString(2, table.getLocationDescription());
            stmt.executeUpdate();
            logger.info("Inserted restaurant table: {}", table);
        } catch (SQLException e) {
            logger.error("Error inserting restaurant table: {}", table, e);
            throw new RuntimeException("Failed to insert restaurant table", e);
        }
    }

    @Override
    public RestaurantTable findById(int id) {
        String sql = "SELECT * FROM restaurant_table WHERE table_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                RestaurantTable table = new RestaurantTable.Builder()
                        .tableId(rs.getInt("table_id"))
                        .capacity(rs.getInt("capacity"))
                        .locationDescription(rs.getString("location_description"))
                        .build();
                logger.info("Fetched restaurant table with ID {}: {}", id, table);
                return table;
            }
        } catch (SQLException e) {
            logger.error("Error fetching restaurant table by ID: {}", id, e);
            throw new RuntimeException("Failed to fetch restaurant table", e);
        }
        logger.warn("No restaurant table found with ID: {}", id);
        return null;
    }

    @Override
    public List<RestaurantTable> findAll() {
        List<RestaurantTable> tables = new ArrayList<>();
        String sql = "SELECT * FROM restaurant_table";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tables.add(new RestaurantTable.Builder()
                        .tableId(rs.getInt("table_id"))
                        .capacity(rs.getInt("capacity"))
                        .locationDescription(rs.getString("location_description"))
                        .build());
            }
            logger.info("Fetched {} restaurant tables", tables.size());
        } catch (SQLException e) {
            logger.error("Error fetching all restaurant tables", e);
            throw new RuntimeException("Failed to fetch all restaurant tables", e);
        }
        return tables;
    }

    @Override
    public void update(RestaurantTable table) {
        String sql = "UPDATE restaurant_table SET capacity = ?, location_description = ? WHERE table_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, table.getCapacity());
            stmt.setString(2, table.getLocationDescription());
            stmt.setInt(3, table.getTableId());
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                logger.info("Updated restaurant table: {}", table);
            } else {
                logger.warn("No restaurant table found to update with ID: {}", table.getTableId());
            }
        } catch (SQLException e) {
            logger.error("Error updating restaurant table: {}", table, e);
            throw new RuntimeException("Failed to update restaurant table", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM restaurant_table WHERE table_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                logger.info("Deleted restaurant table with ID: {}", id);
            } else {
                logger.warn("No restaurant table found to delete with ID: {}", id);
            }
        } catch (SQLException e) {
            logger.error("Error deleting restaurant table with ID: {}", id, e);
            throw new RuntimeException("Failed to delete restaurant table", e);
        }
    }
}
