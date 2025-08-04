package com.upgrad.restaurantmanagementsystem.entities;

public class OrderItem {
    private final int orderItemId;
    private final int orderId;
    private final int menuId;
    private final int quantity;
    private final double totalPrice;

    public int getOrderItemId() {
        return orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }


    private OrderItem(Builder builder) {
        this.orderItemId = builder.orderItemId;
        this.orderId = builder.orderId;
        this.menuId = builder.menuId;
        this.quantity = builder.quantity;
        this.totalPrice = builder.totalPrice;
    }

    public static class Builder {
        private int orderItemId;
        private int orderId;
        private int menuId;
        private int quantity;
        private double totalPrice;

        public Builder orderItemId(int orderItemId) {
            this.orderItemId = orderItemId;
            return this;
        }

        public Builder orderId(int orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder menuId(int menuId) {
            this.menuId = menuId;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder totalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
