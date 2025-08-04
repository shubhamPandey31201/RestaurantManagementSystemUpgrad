package com.upgrad.restaurantmanagementsystem.entities;

public class WaiterDetail {
    private final int userId;
    private final String shift;

    private WaiterDetail(Builder builder) {
        this.userId = builder.userId;
        this.shift = builder.shift;
    }

    public static class Builder {
        private int userId;
        private String shift;

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder shift(String shift) {
            this.shift = shift;
            return this;
        }

        public WaiterDetail build() {
            return new WaiterDetail(this);
        }
    }

    public int getUserId() {
        return userId;
    }

    public String getShift() {
        return shift;
    }

    @Override
    public String toString() {
        return "WaiterDetails{" +
                "userId=" + userId +
                ", shift='" + shift + '\'' +
                '}';
    }
}
