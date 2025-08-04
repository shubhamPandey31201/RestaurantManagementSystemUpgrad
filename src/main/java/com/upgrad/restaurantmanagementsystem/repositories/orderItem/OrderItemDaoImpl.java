package com.upgrad.restaurantmanagementsystem.repositories.orderItem;

import com.upgrad.restaurantmanagementsystem.entities.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.upgrad.restaurantmanagementsystem.ddl.OrderItem.createOrderItemTableIfNotExists;
import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class OrderItemDaoImpl implements OrderItemDao {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemDaoImpl.class);
    private final Connection connection;

    public OrderItemDaoImpl() throws SQLException {
        this.connection = getConnection();
        createOrderItemTableIfNotExists();
    }

    @Override
    public void save(OrderItem item) {
        String sql = "INSERT INTO order_item (order_id, menu_id, quantity, total_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getOrderId());
            stmt.setInt(2, item.getMenuId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getTotalPrice());
            stmt.executeUpdate();
            logger.info("OrderItem saved successfully: {}", item);
        } catch (SQLException e) {
            logger.error("Failed to save OrderItem: {}", item, e);
        }
    }

    @Override
    public OrderItem findById(int id) {
        String sql = "SELECT * FROM order_item WHERE order_item_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                OrderItem item = new OrderItem.Builder()
                        .orderItemId(rs.getInt("order_item_id"))
                        .orderId(rs.getInt("order_id"))
                        .menuId(rs.getInt("menu_id"))
                        .quantity(rs.getInt("quantity"))
                        .totalPrice(rs.getDouble("total_price"))
                        .build();
                logger.info("Found OrderItem with ID {}: {}", id, item);
                return item;
            }
        } catch (SQLException e) {
            logger.error("Failed to find OrderItem with ID: {}", id, e);
        }
        return null;
    }

    @Override
    public List<OrderItem> findAll() {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM order_item";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                OrderItem item = new OrderItem.Builder()
                        .orderItemId(rs.getInt("order_item_id"))
                        .orderId(rs.getInt("order_id"))
                        .menuId(rs.getInt("menu_id"))
                        .quantity(rs.getInt("quantity"))
                        .totalPrice(rs.getDouble("total_price"))
                        .build();
                items.add(item);
            }
            logger.info("Fetched {} order items", items.size());
        } catch (SQLException e) {
            logger.error("Failed to fetch all OrderItems", e);
        }
        return items;
    }

    @Override
    public void update(OrderItem item) {
        String sql = "UPDATE order_item SET order_id = ?, menu_id = ?, quantity = ?, total_price = ? WHERE order_item_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getOrderId());
            stmt.setInt(2, item.getMenuId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getTotalPrice());
            stmt.setInt(5, item.getOrderItemId());
            stmt.executeUpdate();
            logger.info("Updated OrderItem: {}", item);
        } catch (SQLException e) {
            logger.error("Failed to update OrderItem: {}", item, e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM order_item WHERE order_item_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Deleted OrderItem with ID: {}", id);
        } catch (SQLException e) {
            logger.error("Failed to delete OrderItem with ID: {}", id, e);
        }
    }
}
