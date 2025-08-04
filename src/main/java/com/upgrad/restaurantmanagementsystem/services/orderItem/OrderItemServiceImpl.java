package com.upgrad.restaurantmanagementsystem.services.orderItem;

import com.upgrad.restaurantmanagementsystem.entities.OrderItem;
import com.upgrad.restaurantmanagementsystem.repositories.orderItem.OrderItemDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrderItemServiceImpl implements OrderItemService {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemServiceImpl.class);
    private final OrderItemDao orderItemDao;

    public OrderItemServiceImpl(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    @Override
    public void addOrderItem(OrderItem item) {
        try {
            logger.info("Adding OrderItem: {}", item);
            orderItemDao.save(item);
        } catch (Exception e) {
            logger.error("Failed to add OrderItem: {}", item, e);
            throw new RuntimeException("Could not add OrderItem", e);
        }
    }

    @Override
    public OrderItem getOrderItemById(int id) {
        try {
            logger.info("Fetching OrderItem by ID: {}", id);
            return orderItemDao.findById(id);
        } catch (Exception e) {
            logger.error("Failed to fetch OrderItem with ID: {}", id, e);
            throw new RuntimeException("Could not fetch OrderItem", e);
        }
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        try {
            logger.info("Fetching all OrderItems");
            return orderItemDao.findAll();
        } catch (Exception e) {
            logger.error("Failed to fetch all OrderItems", e);
            throw new RuntimeException("Could not fetch OrderItems", e);
        }
    }

    @Override
    public void updateOrderItem(OrderItem item) {
        try {
            logger.info("Updating OrderItem: {}", item);
            orderItemDao.update(item);
        } catch (Exception e) {
            logger.error("Failed to update OrderItem: {}", item, e);
            throw new RuntimeException("Could not update OrderItem", e);
        }
    }

    @Override
    public void removeOrderItem(int id) {
        try {
            logger.info("Removing OrderItem by ID: {}", id);
            orderItemDao.delete(id);
        } catch (Exception e) {
            logger.error("Failed to remove OrderItem with ID: {}", id, e);
            throw new RuntimeException("Could not remove OrderItem", e);
        }
    }
}
