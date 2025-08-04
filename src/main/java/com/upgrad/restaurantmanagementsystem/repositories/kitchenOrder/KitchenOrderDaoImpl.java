package com.upgrad.restaurantmanagementsystem.repositories.kitchenOrder;

import com.upgrad.restaurantmanagementsystem.entities.KitchenOrder;
import com.upgrad.restaurantmanagementsystem.entities.KitchenOrderStatus;
import com.upgrad.restaurantmanagementsystem.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.upgrad.restaurantmanagementsystem.ddl.KitchenOrder.createKitchenOrderTableIfNotExists;
import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class KitchenOrderDaoImpl implements KitchenOrderDao {

    private static final Logger logger = LoggerFactory.getLogger(KitchenOrderDaoImpl.class);

    private final Connection connection;

    public KitchenOrderDaoImpl() throws SQLException {
        this.connection = getConnection();
        createKitchenOrderTableIfNotExists();
    }

    @Override
    public void save(KitchenOrder order) {
        String sql = "INSERT INTO kitchen_order (order_item_id, status, last_updated_time) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getOrderItemId());
            stmt.setString(2, order.getStatus().name());
            stmt.setTimestamp(3, Timestamp.valueOf(order.getLastUpdatedTime()));
            stmt.executeUpdate();
            logger.info("Kitchen order saved for OrderItem ID: {}", order.getOrderItemId());
        } catch (SQLException e) {
            logger.error("Failed to save kitchen order: {}", e.getMessage(), e);
            throw new DatabaseException("Error saving kitchen order", e);
        }
    }

    @Override
    public KitchenOrder findById(int id) {
        String sql = "SELECT * FROM kitchen_order WHERE kitchen_order_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                KitchenOrder order = new KitchenOrder.Builder()
                        .kitchenOrderId(rs.getInt("kitchen_order_id"))
                        .orderItemId(rs.getInt("order_item_id"))
                        .status(KitchenOrderStatus.valueOf(rs.getString("status")))
                        .lastUpdatedTime(rs.getTimestamp("last_updated_time").toLocalDateTime())
                        .build();
                logger.info("Kitchen order retrieved for ID: {}", id);
                return order;
            } else {
                logger.warn("Kitchen order not found with ID: {}", id);
                return null;
            }
        } catch (SQLException e) {
            logger.error("Error retrieving kitchen order with ID {}: {}", id, e.getMessage(), e);
            throw new DatabaseException("Error retrieving kitchen order", e);
        }
    }

    @Override
    public List<KitchenOrder> findAll() {
        List<KitchenOrder> orders = new ArrayList<>();
        String sql = "SELECT * FROM kitchen_order";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                orders.add(new KitchenOrder.Builder()
                        .kitchenOrderId(rs.getInt("kitchen_order_id"))
                        .orderItemId(rs.getInt("order_item_id"))
                        .status(KitchenOrderStatus.valueOf(rs.getString("status")))
                        .lastUpdatedTime(rs.getTimestamp("last_updated_time").toLocalDateTime())
                        .build());
            }
            logger.info("Total kitchen orders retrieved: {}", orders.size());
        } catch (SQLException e) {
            logger.error("Error retrieving kitchen orders: {}", e.getMessage(), e);
            throw new DatabaseException("Error retrieving kitchen orders", e);
        }
        return orders;
    }

    @Override
    public void update(KitchenOrder order) {
        String sql = "UPDATE kitchen_order SET order_item_id = ?, status = ?, last_updated_time = ? WHERE kitchen_order_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getOrderItemId());
            stmt.setString(2, order.getStatus().name());
            stmt.setTimestamp(3, Timestamp.valueOf(order.getLastUpdatedTime()));
            stmt.setInt(4, order.getKitchenOrderId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                logger.warn("No kitchen order updated for ID: {}", order.getKitchenOrderId());
            } else {
                logger.info("Kitchen order updated with ID: {}", order.getKitchenOrderId());
            }
        } catch (SQLException e) {
            logger.error("Failed to update kitchen order: {}", e.getMessage(), e);
            throw new DatabaseException("Error updating kitchen order", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM kitchen_order WHERE kitchen_order_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                logger.warn("No kitchen order found to delete with ID: {}", id);
            } else {
                logger.info("Kitchen order deleted with ID: {}", id);
            }
        } catch (SQLException e) {
            logger.error("Failed to delete kitchen order with ID {}: {}", id, e.getMessage(), e);
            throw new DatabaseException("Error deleting kitchen order", e);
        }
    }
}
