package com.upgrad.restaurantmanagementsystem.entities;

import java.time.LocalDateTime;

public class KitchenOrder {
    private final int kitchenOrderId;
    private final int orderItemId;
    private final KitchenOrderStatus status;
    private final LocalDateTime lastUpdatedTime;

    public int getKitchenOrderId() {
        return kitchenOrderId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public KitchenOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    private KitchenOrder(Builder builder) {
        this.kitchenOrderId = builder.kitchenOrderId;
        this.orderItemId = builder.orderItemId;
        this.status = builder.status;
        this.lastUpdatedTime = builder.lastUpdatedTime;
    }

    public static class Builder {
        private int kitchenOrderId;
        private int orderItemId;
        private KitchenOrderStatus status;
        private LocalDateTime lastUpdatedTime;

        public Builder kitchenOrderId(int kitchenOrderId) {
            this.kitchenOrderId = kitchenOrderId;
            return this;
        }

        public Builder orderItemId(int orderItemId) {
            this.orderItemId = orderItemId;
            return this;
        }

        public Builder status(KitchenOrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder lastUpdatedTime(LocalDateTime lastUpdatedTime) {
            this.lastUpdatedTime = lastUpdatedTime;
            return this;
        }

        public KitchenOrder build() {
            return new KitchenOrder(this);
        }
    }
}
