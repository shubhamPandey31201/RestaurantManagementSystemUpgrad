package com.upgrad.restaurantmanagementsystem.services.restaurantTable;

import com.upgrad.restaurantmanagementsystem.entities.RestaurantTable;
import com.upgrad.restaurantmanagementsystem.repositories.restaurantTable.RestaurantTableDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RestaurantTableServiceImpl implements RestaurantTableService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantTableServiceImpl.class);
    private final RestaurantTableDao tableDao;

    public RestaurantTableServiceImpl(RestaurantTableDao tableDao) {
        this.tableDao = tableDao;
    }

    @Override
    public void createTable(RestaurantTable table) {
        try {
            logger.info("Creating new table: {}", table);
            tableDao.save(table);
        } catch (Exception e) {
            logger.error("Failed to create table: {}", table, e);
            throw new RuntimeException("Could not create table", e);
        }
    }

    @Override
    public RestaurantTable getTableById(int id) {
        try {
            logger.info("Getting table by ID: {}", id);
            return tableDao.findById(id);
        } catch (Exception e) {
            logger.error("Failed to retrieve table with ID: {}", id, e);
            throw new RuntimeException("Could not retrieve table", e);
        }
    }

    @Override
    public List<RestaurantTable> getAllTables() {
        try {
            logger.info("Getting all tables");
            return tableDao.findAll();
        } catch (Exception e) {
            logger.error("Failed to retrieve all tables", e);
            throw new RuntimeException("Could not retrieve all tables", e);
        }
    }

    @Override
    public void modifyTable(RestaurantTable table) {
        try {
            logger.info("Modifying table: {}", table);
            tableDao.update(table);
        } catch (Exception e) {
            logger.error("Failed to modify table: {}", table, e);
            throw new RuntimeException("Could not modify table", e);
        }
    }

    @Override
    public void removeTable(int id) {
        try {
            logger.info("Removing table with ID: {}", id);
            tableDao.delete(id);
        } catch (Exception e) {
            logger.error("Failed to remove table with ID: {}", id, e);
            throw new RuntimeException("Could not remove table", e);
        }
    }
}
