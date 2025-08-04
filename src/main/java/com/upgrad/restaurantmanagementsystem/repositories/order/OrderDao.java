package com.upgrad.restaurantmanagementsystem.repositories.order;

import com.upgrad.restaurantmanagementsystem.entities.Order;

import java.util.List;

public interface OrderDao {
    void save(Order order);
    Order findById(int id);
    List<Order> findAll();
    void update(Order order);
    void delete(int id);
}
