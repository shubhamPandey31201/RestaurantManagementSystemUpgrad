package com.upgrad.restaurantmanagementsystem.repositories.customer;

import com.upgrad.restaurantmanagementsystem.entities.Customer;
import com.upgrad.restaurantmanagementsystem.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.upgrad.restaurantmanagementsystem.ddl.CustomerDdl.createCustomerTableIfNotExists;
import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class CustomerDaoImpl implements CustomerDao {
    private static final Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);
    private final Connection connection;

    public CustomerDaoImpl() throws SQLException {
        this.connection = getConnection();
        createCustomerTableIfNotExists();
    }

    @Override
    public int save(Customer customer) {
        String sql = "INSERT INTO customer (name, phone, email, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhone());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getAddress());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    logger.info("Customer saved with ID: {}", generatedId);
                    return generatedId;
                }
            }
            return -1;
        } catch (SQLException e) {
            logger.error("Error saving customer to database: {}", e.getMessage(), e);
            throw new DatabaseException("Error saving customer to database.", e);
        }
    }

    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                logger.debug("Customer found with ID: {}", id);
                return new Customer.Builder()
                        .customerId(rs.getInt("customer_id"))
                        .name(rs.getString("name"))
                        .phone(rs.getString("phone"))
                        .email(rs.getString("email"))
                        .address(rs.getString("address"))
                        .build();
            }
            return null;
        } catch (SQLException e) {
            logger.error("Error fetching customer with ID {}: {}", id, e.getMessage(), e);
            throw new DatabaseException("Error fetching customer by ID.", e);
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(new Customer.Builder()
                        .customerId(rs.getInt("customer_id"))
                        .name(rs.getString("name"))
                        .phone(rs.getString("phone"))
                        .email(rs.getString("email"))
                        .address(rs.getString("address"))
                        .build());
            }
            logger.info("Total customers fetched: {}", customers.size());
        } catch (SQLException e) {
            logger.error("Error retrieving all customers: {}", e.getMessage(), e);
            throw new DatabaseException("Error retrieving all customers.", e);
        }
        return customers;
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customer SET name = ?, phone = ?, email = ?, address = ? WHERE customer_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhone());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getAddress());
            stmt.setInt(5, customer.getCustomerId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                logger.warn("No customer found to update with ID: {}", customer.getCustomerId());
                throw new DatabaseException("No customer found with ID: " + customer.getCustomerId(), null);
            }
            logger.info("Customer updated with ID: {}", customer.getCustomerId());
        } catch (SQLException e) {
            logger.error("Error updating customer: {}", e.getMessage(), e);
            throw new DatabaseException("Error updating customer.", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                logger.warn("No customer found to delete with ID: {}", id);
                throw new DatabaseException("No customer found to delete with ID: " + id, null);
            }
            logger.info("Customer deleted with ID: {}", id);
        } catch (SQLException e) {
            logger.error("Error deleting customer with ID {}: {}", id, e.getMessage(), e);
            throw new DatabaseException("Error deleting customer.", e);
        }
    }
}
