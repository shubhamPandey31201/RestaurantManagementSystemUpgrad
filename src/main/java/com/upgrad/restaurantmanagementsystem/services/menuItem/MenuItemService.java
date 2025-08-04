package com.upgrad.restaurantmanagementsystem.services.menuItem;

import com.upgrad.restaurantmanagementsystem.entities.MenuItem;

import java.util.List;

public interface MenuItemService {
    void addMenuItem(MenuItem item);
    MenuItem getMenuItemById(int id);
    List<MenuItem> getAllMenuItems();
    void updateMenuItem(MenuItem item);
    void removeMenuItem(int id);
}
