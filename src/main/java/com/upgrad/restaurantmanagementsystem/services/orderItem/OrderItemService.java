package com.upgrad.restaurantmanagementsystem.services.orderItem;

import com.upgrad.restaurantmanagementsystem.entities.OrderItem;

import java.util.List;

public interface OrderItemService {
    void addOrderItem(OrderItem item);
    OrderItem getOrderItemById(int id);
    List<OrderItem> getAllOrderItems();
    void updateOrderItem(OrderItem item);
    void removeOrderItem(int id);
}
