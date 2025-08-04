package com.upgrad.restaurantmanagementsystem.services.salesReport;

import com.upgrad.restaurantmanagementsystem.entities.SalesReport;

import java.util.List;

public interface SalesReportService {
    void createReport(SalesReport report);
    SalesReport getReportById(int id);
    List<SalesReport> getAllReports();
    void updateReport(SalesReport report);
    void deleteReport(int id);
}
