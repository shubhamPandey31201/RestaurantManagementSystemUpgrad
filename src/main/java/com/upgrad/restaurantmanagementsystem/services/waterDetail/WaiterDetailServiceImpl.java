package com.upgrad.restaurantmanagementsystem.services.waterDetail;

import com.upgrad.restaurantmanagementsystem.entities.WaiterDetail;
import com.upgrad.restaurantmanagementsystem.repositories.waiterDetail.WaiterDetailDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaiterDetailServiceImpl implements WaiterDetailService {

    private static final Logger logger = LoggerFactory.getLogger(WaiterDetailServiceImpl.class);
    private final WaiterDetailDao waiterDetailDao;

    public WaiterDetailServiceImpl(WaiterDetailDao waiterDetailDao) {
        this.waiterDetailDao = waiterDetailDao;
    }

    @Override
    public void addWaiterDetails(WaiterDetail waiterDetail) {
        try {
            logger.info("Adding waiter details for user_id: {}", waiterDetail.getUserId());
            waiterDetailDao.save(waiterDetail);
        } catch (RuntimeException e) {
            logger.error("Failed to add waiter details for user_id: {}", waiterDetail.getUserId(), e);
            throw e;
        }
    }

    @Override
    public WaiterDetail getWaiterDetailsByUserId(int userId) {
        try {
            logger.info("Retrieving waiter details for user_id: {}", userId);
            return waiterDetailDao.findByUserId(userId);
        } catch (RuntimeException e) {
            logger.error("Failed to retrieve waiter details for user_id: {}", userId, e);
            throw e;
        }
    }
}
