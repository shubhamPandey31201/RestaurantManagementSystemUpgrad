package com.upgrad.restaurantmanagementsystem.services.tableBooking;

import com.upgrad.restaurantmanagementsystem.entities.TableBooking;
import com.upgrad.restaurantmanagementsystem.repositories.tableBooking.TableBookingDao;
import com.upgrad.restaurantmanagementsystem.repositories.tableBooking.TableBookingDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class TableBookingServiceImpl implements TableBookingService {

    private static final Logger logger = LoggerFactory.getLogger(TableBookingServiceImpl.class);
    private final TableBookingDao bookingDao;

    public TableBookingServiceImpl() throws SQLException {
        this.bookingDao = new TableBookingDaoImpl();
    }

    @Override
    public void bookTable(TableBooking booking) {
        try {
            logger.info("Booking table: {}", booking);
            bookingDao.save(booking);
        } catch (RuntimeException e) {
            logger.error("Failed to book table: {}", booking, e);
            throw e;
        }
    }

    @Override
    public TableBooking getBookingById(int bookingId) {
        try {
            logger.info("Retrieving table booking by ID: {}", bookingId);
            return bookingDao.findById(bookingId);
        } catch (RuntimeException e) {
            logger.error("Failed to retrieve booking by ID: {}", bookingId, e);
            throw e;
        }
    }
}
