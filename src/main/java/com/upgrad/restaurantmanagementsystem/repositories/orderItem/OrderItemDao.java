package com.upgrad.restaurantmanagementsystem.repositories.orderItem;

import com.upgrad.restaurantmanagementsystem.entities.OrderItem;

import java.util.List;

public interface OrderItemDao {
    void save(OrderItem orderItem);
    OrderItem findById(int id);
    List<OrderItem> findAll();
    void update(OrderItem orderItem);
    void delete(int id);

}
