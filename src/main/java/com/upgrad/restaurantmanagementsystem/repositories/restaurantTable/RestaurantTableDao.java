package com.upgrad.restaurantmanagementsystem.repositories.restaurantTable;

import com.upgrad.restaurantmanagementsystem.entities.RestaurantTable;

import java.util.List;

public interface RestaurantTableDao {
    void save(RestaurantTable table);
    RestaurantTable findById(int id);
    List<RestaurantTable> findAll();
    void update(RestaurantTable table);
    void delete(int id);
}
