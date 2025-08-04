package com.upgrad.restaurantmanagementsystem.services.waterDetail;

import com.upgrad.restaurantmanagementsystem.entities.WaiterDetail;


public interface WaiterDetailService {
    void addWaiterDetails(WaiterDetail waiterDetails);
    WaiterDetail getWaiterDetailsByUserId(int userId);
}
