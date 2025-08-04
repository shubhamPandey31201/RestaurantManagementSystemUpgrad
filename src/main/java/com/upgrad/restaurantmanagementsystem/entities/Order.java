package com.upgrad.restaurantmanagementsystem.entities;

import java.util.List;

public class Order {
    private final int orderId;
    private final Customer customer;
    private final List<MenuItem> items;
    private final double totalAmount;

    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.customer = builder.customer;
        this.items = builder.items;
        this.totalAmount = builder.totalAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public static class Builder {
        private int orderId;
        private Customer customer;
        private List<MenuItem> items;
        private double totalAmount;

        public Builder orderId(int orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder items(List<MenuItem> items) {
            this.items = items;
            return this;
        }

        public Builder totalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
