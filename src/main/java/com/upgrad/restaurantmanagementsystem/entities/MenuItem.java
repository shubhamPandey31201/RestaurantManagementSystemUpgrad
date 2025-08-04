package com.upgrad.restaurantmanagementsystem.entities;

public class MenuItem {
    private final int menuId;
    private final String name;
    private final String description;
    private final double price;

    private MenuItem(Builder builder) {
        this.menuId = builder.menuId;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public static class Builder {
        private int menuId;
        private String name;
        private String description;
        private double price;

        public Builder menuId(int menuId) {
            this.menuId = menuId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public MenuItem build() {
            return new MenuItem(this);
        }
    }
}
