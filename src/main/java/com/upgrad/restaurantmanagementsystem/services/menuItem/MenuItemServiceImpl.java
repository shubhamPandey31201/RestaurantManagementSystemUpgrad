package com.upgrad.restaurantmanagementsystem.services.menuItem;

import com.upgrad.restaurantmanagementsystem.entities.MenuItem;
import com.upgrad.restaurantmanagementsystem.repositories.menuItem.MenuItemDao;
import com.upgrad.restaurantmanagementsystem.services.menuItem.MenuItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MenuItemServiceImpl implements MenuItemService {

    private static final Logger logger = LoggerFactory.getLogger(MenuItemServiceImpl.class);
    private final MenuItemDao menuItemDao;

    public MenuItemServiceImpl(MenuItemDao menuItemDao) {
        this.menuItemDao = menuItemDao;
    }

    @Override
    public void addMenuItem(MenuItem item) {
        try {
            logger.info("Adding menu item: {}", item);
            menuItemDao.save(item);
        } catch (Exception e) {
            logger.error("Failed to add menu item: {}", item, e);

        }
    }

    @Override
    public MenuItem getMenuItemById(int id) {
        try {
            logger.info("Retrieving menu item with ID: {}", id);
            return menuItemDao.findById(id);
        } catch (Exception e) {
            logger.error("Failed to retrieve menu item with ID: {}", id, e);
            return null;
        }
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        try {
            logger.info("Retrieving all menu items");
            return menuItemDao.findAll();
        } catch (Exception e) {
            logger.error("Failed to retrieve menu items", e);
            return List.of();
        }
    }

    @Override
    public void updateMenuItem(MenuItem item) {
        try {
            logger.info("Updating menu item: {}", item);
            menuItemDao.update(item);
        } catch (Exception e) {
            logger.error("Failed to update menu item: {}", item, e);
        }
    }

    @Override
    public void removeMenuItem(int id) {
        try {
            logger.info("Removing menu item with ID: {}", id);
            menuItemDao.delete(id);
        } catch (Exception e) {
            logger.error("Failed to remove menu item with ID: {}", id, e);
        }
    }
}
