package com.upgrad.restaurantmanagementsystem.repositories.salesReport;

import com.upgrad.restaurantmanagementsystem.entities.SalesReport;

import java.util.List;

public interface SalesReportDao {
    void save(SalesReport report);
    SalesReport findById(int reportId);
    List<SalesReport> findAll();
    void update(SalesReport report);
    void delete(int reportId);
}
