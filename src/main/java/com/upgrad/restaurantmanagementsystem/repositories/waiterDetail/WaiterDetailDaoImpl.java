package com.upgrad.restaurantmanagementsystem.repositories.waiterDetail;

import com.upgrad.restaurantmanagementsystem.entities.WaiterDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import static com.upgrad.restaurantmanagementsystem.ddl.WaiterDetail.createWaiterDetailTableIfNotExists;

public class WaiterDetailDaoImpl implements WaiterDetailDao {

    private static final Logger logger = LoggerFactory.getLogger(WaiterDetailDaoImpl.class);
    private final Connection connection;

    public WaiterDetailDaoImpl(Connection connection) throws SQLException {
        this.connection = connection;
        createWaiterDetailTableIfNotExists();
        logger.info("WaiterDetail table ensured and WaiterDetailDaoImpl initialized.");
    }

    @Override
    public void save(WaiterDetail waiterDetail) {
        String sql = "INSERT INTO waiter_details (user_id, shift) VALUES (?, ?) " +
                "ON CONFLICT (user_id) DO UPDATE SET shift = EXCLUDED.shift";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, waiterDetail.getUserId());
            pstmt.setString(2, waiterDetail.getShift());
            pstmt.executeUpdate();
            logger.info("Waiter detail saved for user_id: {}", waiterDetail.getUserId());
        } catch (SQLException e) {
            logger.error("Failed to save waiter detail for user_id: {}", waiterDetail.getUserId(), e);
            throw new RuntimeException("Error saving waiter details", e);
        }
    }

    @Override
    public WaiterDetail findByUserId(int userId) {
        String sql = "SELECT * FROM waiter_details WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                logger.info("Found waiter details for user_id: {}", userId);
                return new WaiterDetail.Builder()
                        .userId(rs.getInt("user_id"))
                        .shift(rs.getString("shift"))
                        .build();
            } else {
                logger.warn("No waiter details found for user_id: {}", userId);
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch waiter detail for user_id: {}", userId, e);
            throw new RuntimeException("Error retrieving waiter details", e);
        }
        return null;
    }
}
