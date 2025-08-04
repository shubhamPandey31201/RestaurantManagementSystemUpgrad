package com.upgrad.restaurantmanagementsystem.services.salesReport;

import com.upgrad.restaurantmanagementsystem.entities.SalesReport;
import com.upgrad.restaurantmanagementsystem.repositories.salesReport.SalesReportDao;
import com.upgrad.restaurantmanagementsystem.repositories.salesReport.SalesReportDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class SalesReportServiceImpl implements SalesReportService {

    private static final Logger logger = LoggerFactory.getLogger(SalesReportServiceImpl.class);
    private final SalesReportDao salesReportDao;

    public SalesReportServiceImpl() throws SQLException {
        this.salesReportDao = new SalesReportDaoImpl();
    }

    @Override
    public void createReport(SalesReport report) {
        try {
            logger.info("Creating SalesReport: {}", report);
            salesReportDao.save(report);
        } catch (Exception e) {
            logger.error("Failed to create SalesReport: {}", report, e);
            throw new RuntimeException("Failed to create SalesReport", e);
        }
    }

    @Override
    public SalesReport getReportById(int id) {
        try {
            logger.info("Fetching SalesReport by ID: {}", id);
            return salesReportDao.findById(id);
        } catch (Exception e) {
            logger.error("Failed to fetch SalesReport with ID: {}", id, e);
            throw new RuntimeException("Failed to fetch SalesReport", e);
        }
    }

    @Override
    public List<SalesReport> getAllReports() {
        try {
            logger.info("Fetching all SalesReports");
            return salesReportDao.findAll();
        } catch (Exception e) {
            logger.error("Failed to fetch all SalesReports", e);
            throw new RuntimeException("Failed to fetch SalesReports", e);
        }
    }

    @Override
    public void updateReport(SalesReport report) {
        try {
            logger.info("Updating SalesReport: {}", report);
            salesReportDao.update(report);
        } catch (Exception e) {
            logger.error("Failed to update SalesReport: {}", report, e);
            throw new RuntimeException("Failed to update SalesReport", e);
        }
    }

    @Override
    public void deleteReport(int id) {
        try {
            logger.info("Deleting SalesReport with ID: {}", id);
            salesReportDao.delete(id);
        } catch (Exception e) {
            logger.error("Failed to delete SalesReport with ID: {}", id, e);
            throw new RuntimeException("Failed to delete SalesReport", e);
        }
    }
}
