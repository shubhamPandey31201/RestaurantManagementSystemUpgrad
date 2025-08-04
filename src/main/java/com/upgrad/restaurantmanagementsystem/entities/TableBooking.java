package com.upgrad.restaurantmanagementsystem.entities;

import java.time.LocalDateTime;

public class TableBooking {
    private final int bookingId;
    private final int tableNumber;
    private final Customer customer;
    private final LocalDateTime bookingTime;

    private TableBooking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.tableNumber = builder.tableNumber;
        this.customer = builder.customer;
        this.bookingTime = builder.bookingTime;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    @Override
    public String toString() {
        return "TableBooking{" +
                "bookingId=" + bookingId +
                ", tableNumber=" + tableNumber +
                ", customer=" + customer +
                ", bookingTime=" + bookingTime +
                '}';
    }

    public static class Builder {
        private int bookingId;
        private int tableNumber;
        private Customer customer;
        private LocalDateTime bookingTime;

        public Builder bookingId(int bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder tableNumber(int tableNumber) {
            this.tableNumber = tableNumber;
            return this;
        }

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder bookingTime(LocalDateTime bookingTime) {
            this.bookingTime = bookingTime;
            return this;
        }

        public TableBooking build() {
            return new TableBooking(this);
        }
    }
}
