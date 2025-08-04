package com.upgrad.restaurantmanagementsystem.repositories.order;

import com.upgrad.restaurantmanagementsystem.entities.Order;
import com.upgrad.restaurantmanagementsystem.entities.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.upgrad.restaurantmanagementsystem.ddl.Order.createOrderTableIfNotExists;
import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class OrderDaoImpl implements OrderDao {

    private static final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);
    private final Connection connection;

    public OrderDaoImpl() throws SQLException {
        this.connection = getConnection();
        createOrderTableIfNotExists();
    }

    @Override
    public void save(Order order) {
        String sql = "INSERT INTO orders (customer_id, total_amount) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getCustomer().getCustomerId());
            stmt.setDouble(2, order.getTotalAmount());
            stmt.executeUpdate();
            logger.info("Order saved successfully: {}", order);
        } catch (SQLException e) {
            logger.error("Error saving order: {}", order, e);
        }
    }

    @Override
    public Order findById(int id) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Order order = new Order.Builder()
                        .orderId(rs.getInt("order_id"))
                        .customer(new Customer.Builder().customerId(rs.getInt("customer_id")).build())
                        .items(new ArrayList<>()) // items loading can be handled separately
                        .totalAmount(rs.getDouble("total_amount"))
                        .build();
                logger.info("Order retrieved with ID {}: {}", id, order);
                return order;
            }
        } catch (SQLException e) {
            logger.error("Error finding order with ID: {}", id, e);
        }
        return null;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                orders.add(new Order.Builder()
                        .orderId(rs.getInt("order_id"))
                        .customer(new Customer.Builder().customerId(rs.getInt("customer_id")).build())
                        .items(new ArrayList<>())
                        .totalAmount(rs.getDouble("total_amount"))
                        .build());
            }
            logger.info("Total orders retrieved: {}", orders.size());
        } catch (SQLException e) {
            logger.error("Error retrieving all orders", e);
        }
        return orders;
    }

    @Override
    public void update(Order order) {
        String sql = "UPDATE orders SET customer_id = ?, total_amount = ? WHERE order_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getCustomer().getCustomerId());
            stmt.setDouble(2, order.getTotalAmount());
            stmt.setInt(3, order.getOrderId());
            stmt.executeUpdate();
            logger.info("Order updated successfully: {}", order);
        } catch (SQLException e) {
            logger.error("Error updating order: {}", order, e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Order deleted with ID: {}", id);
        } catch (SQLException e) {
            logger.error("Error deleting order with ID: {}", id, e);
        }
    }
}
