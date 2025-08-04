package com.upgrad.restaurantmanagementsystem.services.kitchenOrder;

import com.upgrad.restaurantmanagementsystem.entities.KitchenOrder;

import java.util.List;

public interface KitchenOrderService {
    void createOrder(KitchenOrder order);
    KitchenOrder getOrder(int id);
    List<KitchenOrder> getAllOrders();
    void updateOrder(KitchenOrder order);
    void deleteOrder(int id);
}
