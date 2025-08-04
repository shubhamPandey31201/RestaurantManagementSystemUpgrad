package com.upgrad.restaurantmanagementsystem.repositories.tableBooking;

import com.upgrad.restaurantmanagementsystem.entities.TableBooking;
import com.upgrad.restaurantmanagementsystem.entities.Customer;
import com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil;
import com.upgrad.restaurantmanagementsystem.repositories.customer.CustomerDao;
import com.upgrad.restaurantmanagementsystem.repositories.customer.CustomerDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import static com.upgrad.restaurantmanagementsystem.ddl.TableBooking.createTableBookingTableIfNotExists;
import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class TableBookingDaoImpl implements TableBookingDao {

    private static final Logger logger = LoggerFactory.getLogger(TableBookingDaoImpl.class);
    private final Connection connection;
    private final CustomerDao customerDao;

    public TableBookingDaoImpl() throws SQLException {
        this.customerDao = new CustomerDaoImpl();
        this.connection = getConnection();
        createTableBookingTableIfNotExists();
    }

    @Override
    public void save(TableBooking booking) {
        String sql = "INSERT INTO table_booking (table_number, customer_id, booking_time) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, booking.getTableNumber());
            ps.setInt(2, booking.getCustomer().getCustomerId());
            ps.setTimestamp(3, Timestamp.valueOf(booking.getBookingTime()));
            ps.executeUpdate();
            logger.info("Table booking saved successfully: {}", booking);
        } catch (SQLException e) {
            logger.error("Error saving table booking: {}", booking, e);
            throw new RuntimeException("Failed to save table booking", e);
        }
    }

    @Override
    public TableBooking findById(int bookingId) {
        String sql = "SELECT * FROM table_booking WHERE booking_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int customerId = rs.getInt("customer_id");
                Customer customer = customerDao.findById(customerId);

                TableBooking booking = new TableBooking.Builder()
                        .bookingId(rs.getInt("booking_id"))
                        .tableNumber(rs.getInt("table_number"))
                        .customer(customer)
                        .bookingTime(rs.getTimestamp("booking_time").toLocalDateTime())
                        .build();

                logger.info("Found table booking: {}", booking);
                return booking;
            } else {
                logger.warn("No table booking found with ID: {}", bookingId);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving booking by ID: {}", bookingId, e);
            throw new RuntimeException("Failed to find table booking by ID", e);
        }
        return null;
    }
}
