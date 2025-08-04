package com.upgrad.restaurantmanagementsystem.entities;

public class RestaurantTable {
    private final int tableId;
    private final int capacity;
    private final String locationDescription;

    private RestaurantTable(Builder builder) {
        this.tableId = builder.tableId;
        this.capacity = builder.capacity;
        this.locationDescription = builder.locationDescription;
    }

    public int getTableId() {
        return tableId;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public static class Builder {
        private int tableId;
        private int capacity;
        private String locationDescription;

        public Builder tableId(int tableId) {
            this.tableId = tableId;
            return this;
        }

        public Builder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder locationDescription(String locationDescription) {
            this.locationDescription = locationDescription;
            return this;
        }

        public RestaurantTable build() {
            return new RestaurantTable(this);
        }
    }
}
