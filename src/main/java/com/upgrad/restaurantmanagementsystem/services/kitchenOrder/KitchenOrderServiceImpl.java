package com.upgrad.restaurantmanagementsystem.services.kitchenOrder;

import com.upgrad.restaurantmanagementsystem.entities.KitchenOrder;
import com.upgrad.restaurantmanagementsystem.repositories.kitchenOrder.KitchenOrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class KitchenOrderServiceImpl implements KitchenOrderService {

    private static final Logger logger = LoggerFactory.getLogger(KitchenOrderServiceImpl.class);
    private final KitchenOrderDao kitchenOrderDao;

    public KitchenOrderServiceImpl(KitchenOrderDao kitchenOrderDao) {
        this.kitchenOrderDao = kitchenOrderDao;
    }

    @Override
    public void createOrder(KitchenOrder order) {
        try {
            kitchenOrderDao.save(order);
            logger.info("Kitchen order created for OrderItem ID: {}", order.getOrderItemId());
        } catch (Exception e) {
            logger.error("Failed to create kitchen order: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public KitchenOrder getOrder(int id) {
        try {
            KitchenOrder order = kitchenOrderDao.findById(id);
            if (order == null) {
                logger.warn("Kitchen order not found for ID: {}", id);
            }
            return order;
        } catch (Exception e) {
            logger.error("Failed to retrieve kitchen order with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<KitchenOrder> getAllOrders() {
        try {
            List<KitchenOrder> orders = kitchenOrderDao.findAll();
            logger.info("Fetched all kitchen orders: {}", orders.size());
            return orders;
        } catch (Exception e) {
            logger.error("Failed to retrieve all kitchen orders: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void updateOrder(KitchenOrder order) {
        try {
            kitchenOrderDao.update(order);
            logger.info("Updated kitchen order with ID: {}", order.getKitchenOrderId());
        } catch (Exception e) {
            logger.error("Failed to update kitchen order: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteOrder(int id) {
        try {
            kitchenOrderDao.delete(id);
            logger.info("Deleted kitchen order with ID: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete kitchen order: {}", e.getMessage(), e);
            throw e;
        }
    }
}
