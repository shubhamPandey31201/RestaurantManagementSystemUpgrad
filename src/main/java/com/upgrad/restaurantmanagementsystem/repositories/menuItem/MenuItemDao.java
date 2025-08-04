package com.upgrad.restaurantmanagementsystem.repositories.menuItem;

import com.upgrad.restaurantmanagementsystem.entities.MenuItem;

import java.util.List;

public interface MenuItemDao {
    void save(MenuItem item);
    MenuItem findById(int id);
    List<MenuItem> findAll();
    void update(MenuItem item);
    void delete(int id);
}
