package com.upgrad.restaurantmanagementsystem.repositories.waiterDetail;

import com.upgrad.restaurantmanagementsystem.entities.WaiterDetail;


public interface WaiterDetailDao {
    void save(WaiterDetail waiterDetail);
    WaiterDetail findByUserId(int userId);
}
