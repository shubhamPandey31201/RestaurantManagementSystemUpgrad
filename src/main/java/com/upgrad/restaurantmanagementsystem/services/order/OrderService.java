package com.upgrad.restaurantmanagementsystem.services.order;

import com.upgrad.restaurantmanagementsystem.entities.Order;

import java.util.List;

public interface OrderService {
    void createOrder(Order order);
    Order getOrder(int id);
    List<Order> getAllOrders();
    void updateOrder(Order order);
    void deleteOrder(int id);
}
