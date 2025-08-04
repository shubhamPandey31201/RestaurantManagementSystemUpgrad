package com.upgrad.restaurantmanagementsystem.repositories.tableBooking;

import com.upgrad.restaurantmanagementsystem.entities.TableBooking;

public interface TableBookingDao {
    void save(TableBooking booking);
    TableBooking findById(int bookingId);
}
