package com.upgrad.restaurantmanagementsystem.entities;

import java.time.LocalDateTime;

public class SalesReport {
    private final int reportId;
    private final int generatedByUserId;
    private final LocalDateTime generatedDate;
    private final double totalRevenue;
    private final int totalOrders;

    public int getReportId() {
        return reportId;
    }

    public int getGeneratedByUserId() {
        return generatedByUserId;
    }

    public LocalDateTime getGeneratedDate() {
        return generatedDate;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public int getTotalOrders() {
        return totalOrders;
    }


    private SalesReport(Builder builder) {
        this.reportId = builder.reportId;
        this.generatedByUserId = builder.generatedByUserId;
        this.generatedDate = builder.generatedDate;
        this.totalRevenue = builder.totalRevenue;
        this.totalOrders = builder.totalOrders;
    }

    public static class Builder {
        private int reportId;
        private int generatedByUserId;
        private LocalDateTime generatedDate;
        private double totalRevenue;
        private int totalOrders;

        public Builder reportId(int reportId) {
            this.reportId = reportId;
            return this;
        }

        public Builder generatedByUserId(int generatedByUserId) {
            this.generatedByUserId = generatedByUserId;
            return this;
        }

        public Builder generatedDate(LocalDateTime generatedDate) {
            this.generatedDate = generatedDate;
            return this;
        }

        public Builder totalRevenue(double totalRevenue) {
            this.totalRevenue = totalRevenue;
            return this;
        }

        public Builder totalOrders(int totalOrders) {
            this.totalOrders = totalOrders;
            return this;
        }

        public SalesReport build() {
            return new SalesReport(this);
        }
    }
}
