package com.upgrad.restaurantmanagementsystem.repositories.menuItem;

import com.upgrad.restaurantmanagementsystem.entities.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.upgrad.restaurantmanagementsystem.ddl.MenuItem.createMenuItemTableIfNotExists;
import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class MenuItemDaoImpl implements MenuItemDao {

    private static final Logger logger = LoggerFactory.getLogger(MenuItemDaoImpl.class);
    private final Connection connection;

    public MenuItemDaoImpl() throws SQLException {
        this.connection = getConnection();
        createMenuItemTableIfNotExists();
    }

    @Override
    public void save(MenuItem item) {
        String sql = "INSERT INTO menu_item (name, description, price) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error saving menu item: {}", item, e);
        }
    }

    @Override
    public MenuItem findById(int id) {
        String sql = "SELECT * FROM menu_item WHERE menu_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new MenuItem.Builder()
                        .menuId(rs.getInt("menu_id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getDouble("price"))
                        .build();
            }
        } catch (SQLException e) {
            logger.error("Error finding menu item with ID: {}", id, e);
        }
        return null;
    }

    @Override
    public List<MenuItem> findAll() {
        List<MenuItem> items = new ArrayList<>();
        String sql = "SELECT * FROM menu_item";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                items.add(new MenuItem.Builder()
                        .menuId(rs.getInt("menu_id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getDouble("price"))
                        .build());
            }
        } catch (SQLException e) {
            logger.error("Error retrieving all menu items", e);
        }
        return items;
    }

    @Override
    public void update(MenuItem item) {
        String sql = "UPDATE menu_item SET name = ?, description = ?, price = ? WHERE menu_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getMenuId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error updating menu item: {}", item, e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM menu_item WHERE menu_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting menu item with ID: {}", id, e);
        }
    }
}
