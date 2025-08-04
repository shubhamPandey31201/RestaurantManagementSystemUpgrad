package com.upgrad.restaurantmanagementsystem.repositories.kitchenOrder;

import com.upgrad.restaurantmanagementsystem.entities.KitchenOrder;

import java.util.List;

public interface KitchenOrderDao {
    void save(KitchenOrder order);
    KitchenOrder findById(int id);
    List<KitchenOrder> findAll();
    void update(KitchenOrder order);
    void delete(int id);
}
