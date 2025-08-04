package com.upgrad.restaurantmanagementsystem.services.order;

import com.upgrad.restaurantmanagementsystem.entities.Order;
import com.upgrad.restaurantmanagementsystem.repositories.order.OrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void createOrder(Order order) {
        try {
            orderDao.save(order);
            logger.info("Order creation successful: {}", order);
        } catch (Exception e) {
            logger.error("Failed to create order: {}", order, e);
        }
    }

    @Override
    public Order getOrder(int id) {
        try {
            return orderDao.findById(id);
        } catch (Exception e) {
            logger.error("Failed to retrieve order with ID: {}", id, e);
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders() {
        try {
            return orderDao.findAll();
        } catch (Exception e) {
            logger.error("Failed to retrieve all orders", e);
            return List.of();
        }
    }

    @Override
    public void updateOrder(Order order) {
        try {
            orderDao.update(order);
            logger.info("Order updated: {}", order);
        } catch (Exception e) {
            logger.error("Failed to update order: {}", order, e);
        }
    }

    @Override
    public void deleteOrder(int id) {
        try {
            orderDao.delete(id);
            logger.info("Order deleted with ID: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete order with ID: {}", id, e);
        }
    }
}
