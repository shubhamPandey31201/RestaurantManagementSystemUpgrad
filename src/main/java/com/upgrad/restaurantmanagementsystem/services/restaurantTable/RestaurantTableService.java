package com.upgrad.restaurantmanagementsystem.services.restaurantTable;

import com.upgrad.restaurantmanagementsystem.entities.RestaurantTable;

import java.util.List;

public interface RestaurantTableService {
    void createTable(RestaurantTable table);
    RestaurantTable getTableById(int id);
    List<RestaurantTable> getAllTables();
    void modifyTable(RestaurantTable table);
    void removeTable(int id);
}
