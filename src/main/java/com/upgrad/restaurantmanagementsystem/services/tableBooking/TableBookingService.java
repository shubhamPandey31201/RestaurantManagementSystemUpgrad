package com.upgrad.restaurantmanagementsystem.services.tableBooking;

import com.upgrad.restaurantmanagementsystem.entities.TableBooking;

public interface TableBookingService {
    void bookTable(TableBooking booking);
    TableBooking getBookingById(int bookingId);
}
